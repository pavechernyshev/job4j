package ru.job4j.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.log.UsageLog4j2;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.InputStream;
import java.util.Properties;

public class TrackerSQL implements ITracker, AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());
    private Connection connection;

    public boolean init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            if (this.connection != null) {
                DBStructureCreator dbStructureCreator = new DBStructureCreator(this.connection);
                dbStructureCreator.createIfNotExist();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this.connection != null;
    }


    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    @Override
    public Item add(Item item) {
        String sql = "insert into items (name, description, created) values (?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            Timestamp created;
            if (item.getCreated() != 0) {
                created = new Timestamp(item.getCreated());
            } else {
                created = new Timestamp(new Date().getTime());
            }
            ps.setTimestamp(3, created);
            ps.execute();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                item.setId(String.valueOf(id));
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }

        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        String sql = "update items set name=?, description=?, created=? where id=?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.setTimestamp(3, new Timestamp(item.getCreated()));
            ps.setInt(4, Integer.parseInt(id));
            ps.execute();
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        boolean res = false;
        if (id.length() > 0) {
            try (PreparedStatement ps = this.connection.prepareStatement("delete from items where id=?")) {
                ps.setInt(1, Integer.parseInt(id));
                ps.execute();
                res = true;
            } catch (SQLException e) {
                LOG.catching(e);
            }
        }
        return res;
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new LinkedList<>();
        String sql = "select * from items";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = getItemByResultSet(rs);
                list.add(item);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new LinkedList<>();
        String sql = "select * from items where name=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = getItemByResultSet(rs);
                list.add(item);
            }

        } catch (SQLException e) {
            LOG.catching(e);
        }
        return list;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from items where id=?")) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = getItemByResultSet(rs);
            }
        } catch (SQLException e) {
            LOG.catching(e);
        }
        return item;
    }

    private Item getItemByResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(String.valueOf(rs.getInt("id")));
        item.setName(rs.getString("name"));
        item.setDesc(rs.getString("description"));
        item.setCreated(rs.getTimestamp("created").getTime());
        return item;
    }
}
