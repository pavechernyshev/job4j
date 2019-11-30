package ru.job4j.models;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.log.UsageLog4j2;
import ru.job4j.persistent.Store;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbStore implements Store {

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
        String sql = "insert into users (login, name, email) values (?, ?, ?)";
        try (Connection connection = SOURCE.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.execute();
            connection.commit();
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
            ps.setInt(1, id);
            ps.execute();
            connection.commit();
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
        String sql = "select * from users";
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
                PreparedStatement ps = connection.prepareStatement("select * from users where id=?")) {
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
                PreparedStatement ps = connection.prepareStatement("select * from users where login=?")) {
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
        return new User(id, name, login, email);
    }
}
