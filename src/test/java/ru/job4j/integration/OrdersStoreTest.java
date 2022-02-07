package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("postgres");
        pool.setPassword("password");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void  whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(InOrder.of("name1", "description1"));
        store.save(InOrder.of("name2", "description2"));
        List<InOrder> all = (List<InOrder>) store.findAll();

        assertThat(all.size(), is(2));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenUpdateInOrder() {
        OrdersStore store = new OrdersStore(pool);
        InOrder inOrder = store.save(InOrder.of("name1", "description1"));
        inOrder.setName("UpdatedName");
        store.updateOrder(inOrder);
        assertThat(store.findById(1).getName(), is("UpdatedName"));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(InOrder.of("Petr", "senior"));
        store.save(InOrder.of("Ivan", "junior"));
        InOrder inOrder = store.findByName("Petr");
        assertEquals(inOrder.getDescription(), "senior");
    }

    @After
    public void deleteTable() {
        OrdersStore store = new OrdersStore(pool);
        store.deleteTable();
    }
}