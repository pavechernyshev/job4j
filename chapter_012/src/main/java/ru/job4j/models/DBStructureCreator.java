package ru.job4j.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBStructureCreator implements AutoCloseable {

    private Connection connection;

    public DBStructureCreator(Connection connection) {
        this.connection = connection;
    }

    public void createIfNotExist() throws SQLException {
        createUsers();
    }

    private void createUsers() throws SQLException {
        String sql = "create table if not exists users (id serial primary key, login varchar (60) unique, name varchar (60), email varchar (60), photo_id varchar(255), password varchar (255)) ;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
