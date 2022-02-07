package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.ds.common.BaseDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrdersStore {
    private final BasicDataSource pool;

    public OrdersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public InOrder save(InOrder inOrder) {
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement(
                     "INSERT INTO inorders(name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, inOrder.getName());
            pr.setString(2, inOrder.getDescription());
            pr.setTimestamp(3, inOrder.getCreated());
            pr.execute();
            ResultSet id = pr.getGeneratedKeys();
            if (id.next()) {
                inOrder.setId(id.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inOrder;
    }

    public Collection<InOrder> findAll() {
        List<InOrder> rsl = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM inorders")) {
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    rsl.add(
                            new InOrder(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("description"),
                                    rs.getTimestamp(4)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public InOrder findById(int id) {
        InOrder rsl = null;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM inorders WHERE id = ?")) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                rsl = new InOrder(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public InOrder updateOrder(InOrder inOrder) {
        InOrder rsl = null;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("update inorders set name = ?, "
                     + "description = ?, created = ? WHERE id = ?")) {
            pr.setString(1, inOrder.getName());
            pr.setString(2, inOrder.getDescription());
            pr.setTimestamp(3, inOrder.getCreated());
            pr.setInt(4, inOrder.getId());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public InOrder findByName(String name) {
        InOrder rsl = null;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM inorders WHERE name = ?")) {
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                rsl = new InOrder(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public void deleteTable() {
        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate("drop table if exists inorders");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
