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
        createRoles();
        createUsersToRoles();
        connection.commit();
    }

    private void createUsers() throws SQLException {
        String sql = "create table if not exists users (" +
                "id serial primary key, " +
                "login varchar (60) unique, " +
                "name varchar (60), " +
                "email varchar (60), " +
                "photo_id varchar(255), " +
                "password varchar (255)) ;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    private void createRoles() throws SQLException {
        String sql = "create table if not exists roles (" +
                "id serial primary key, " +
                "name varchar (60)) ;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    private void createUsersToRoles() throws SQLException {
        String sql = "create table if not exists public.user_role (" +
                "user_id integer references users(id)," +
                "role_id integer references roles(id)," +
                "CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)) ;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
