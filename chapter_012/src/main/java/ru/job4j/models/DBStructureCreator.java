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
        String sql = new StringBuilder().append("create table if not exists users (")
                .append("id serial primary key, ")
                .append("login varchar (60) unique, ")
                .append("name varchar (60), ")
                .append("email varchar (60), ")
                .append("photo_id varchar(255), ")
                .append("password varchar (255)) ;")
                .toString();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    private void createRoles() throws SQLException {
        String sql = new StringBuilder()
                .append("create table if not exists roles (")
                .append("id serial primary key, ")
                .append("name varchar (60)) ;")
                .toString();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    private void createUsersToRoles() throws SQLException {
        String sql = new StringBuilder()
                .append("create table if not exists public.user_role (")
                .append("user_id integer references users(id),")
                .append("role_id integer references roles(id),")
                .append("CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)) ;")
                .toString();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
