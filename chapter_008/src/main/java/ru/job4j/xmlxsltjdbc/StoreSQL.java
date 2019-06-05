package ru.job4j.xmlxsltjdbc;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StoreSQL implements AutoCloseable {
    private final Config config;
    private Connection connect;
    private DatabaseMetaData metaData;

    public StoreSQL(Config config) {
        this.config = config;
    }

    public boolean init() {
        boolean res = false;
        try {
            Connection conn = DriverManager.getConnection(
                    config.get("url"),
                    config.get("username"),
                    config.get("password"));
            if (conn != null) {
                this.connect = conn;
                this.metaData = conn.getMetaData();
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void generate(int size) {
        this.createEntryTable();
        for (int i = 1; i <= size; i++) {
            String sql = "insert into entries (field) values (?);";
            try (PreparedStatement ps = this.connect.prepareStatement(sql)) {
                ps.setInt(1, i);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Entry> load() {
        List<Entry> result = new LinkedList<>();
        String sql = "select * from entries";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Entry entry = new Entry(rs.getInt("field"));
                result.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void clear() {
        try (PreparedStatement ps = connect.prepareStatement("delete from entries;")) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }

    private void createEntryTable() {
        String sql = "create table if not exists entries (field integer );";
        try (PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class Entry {
        private int field;

        public Entry(int field) {
            this.field = field;
        }

        public int getField() {
            return field;
        }

        public void setField(int field) {
            this.field = field;
        }
    }
}
