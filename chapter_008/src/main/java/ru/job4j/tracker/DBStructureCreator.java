package ru.job4j.tracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBStructureCreator implements AutoCloseable {

    private Connection connection;

    public DBStructureCreator(Connection connection) {
        this.connection = connection;
    }

    public void createIfNotExist() throws SQLException {
        createItems();
        createComments();
    }

    private void createComments() throws SQLException {
        String sql = "create table if not exists comments (id serial primary key, item_id integer references items(id),text varchar (2000));";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    private void createItems() throws SQLException {
        String sql = "create table if not exists items (id serial primary key, name varchar (200), description varchar (2000), created timestamp);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
