package ru.job4j.persistent;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.log.UsageLog4j2;
import ru.job4j.models.DBStructureCreator;
import ru.job4j.models.User;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbStore implements Store, Role {

    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());

    public DbStore() {
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/tracker");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        SOURCE.setDefaultAutoCommit(false);
        try {
            DBStructureCreator dbStructureCreator = new DBStructureCreator(SOURCE.getConnection());
            dbStructureCreator.createIfNotExist();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    public static DbStore getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        if (findByLogin(user.getLogin()) != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }
        String sql = "insert into users (login, name, email, photo_id, password) values (?, ?, ?, ?, ?)";
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPhotoId());
                MessageDigest md = MessageDigest.getInstance("MD5");
                ps.setString(5, DigestUtils.md5Hex(user.getPassword() + user.getLogin()));
                ps.execute();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    user.setId(id);
                }
                connection.commit();
            sql = "insert into user_role (user_id, role_id) values (?, ?)";
            try (PreparedStatement psRole = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                psRole.setInt(1, user.getId());
                psRole.setInt(2, user.getRole().getId());
                psRole.execute();
                connection.commit();
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            try {
                SOURCE.getConnection().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            LOG.catching(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        String sql = "update users set login=?, name=?, email=? where id=?";
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getId());
            ps.execute();
            connection.commit();
            sql = "update user_role set role_id=? where user_id=?";
            try (PreparedStatement psRole = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                psRole.setInt(1, user.getRole().getId());
                psRole.setInt(2, user.getId());
                psRole.execute();
                connection.commit();
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            try {
                SOURCE.getConnection().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            LOG.catching(e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        boolean success = true;
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement("delete from users where id=?")) {
            User user = this.findById(id);
            ps.setInt(1, id);
            ps.execute();
            connection.commit();
            if (user.getPhotoId().length() > 0) {
                File file = new File("images" + File.separator + user.getPhotoId());
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (SQLException e) {
            try (Connection connection = SOURCE.getConnection()) {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            LOG.catching(e);
            success = false;
        }
        return success;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new LinkedList<>();
        String sql = "select u.*, r.id as role_id, r.name as role_name from users as u " +
                "inner join user_role as ur ON u.id = ur.user_id " +
                "left join roles as r ON r.id = ur.role_id ";
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = getUserByResultSet(rs);
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return list;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement("select u.*, r.id as role_id, r.name as role_name from users as u " +
                        "inner join user_role as ur ON u.id = ur.user_id " +
                        "left join roles as r ON r.id = ur.role_id " +
                        "where u.id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getUserByResultSet(rs);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return user;

    }

    public User findByLogin(String login) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement("select u.*, r.id as role_id, r.name as role_name from users as u " +
                        "inner join user_role as ur ON u.id = ur.user_id " +
                        "left join roles as r ON r.id = ur.role_id " +
                        "where u.login = ?")) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = getUserByResultSet(rs);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return user;
    }


    private User getUserByResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        String photoId = rs.getString("photo_id");
        String password = rs.getString("password");
        int roleId = rs.getInt("role_id");
        String roleName = rs.getString("role_name");
        User user = new User(id, name, login, email);
        user.setPhotoId(photoId);
        user.setPassword(password);
        user.setRole(new ru.job4j.models.Role(roleId, roleName));
        return user;
    }

    public boolean isCredentional(String login, String password) {
        User user = this.findByLogin(login);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(DigestUtils.md5Hex(password + login));
    }

    @Override
    public ru.job4j.models.Role getUserRole(User user) {
        ru.job4j.models.Role role = null;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from user_role where user_id=?")) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt("role_id");
                role = getRoleById(roleId);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return role;
    }

    private ru.job4j.models.Role getRoleById(int id) {
        ru.job4j.models.Role role = null;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from roles where id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                role = new ru.job4j.models.Role(id, name);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return role;
    }

    @Override
    public List<ru.job4j.models.Role> getRoles() {
        List<ru.job4j.models.Role> listRole = new LinkedList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from roles")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                listRole.add(new ru.job4j.models.Role(id, name));
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return listRole;
    }
}
