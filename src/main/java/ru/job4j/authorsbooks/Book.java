package ru.job4j.authorsbooks;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Author> authors = new ArrayList<>();

    public static Book of(String name) {
        Book book = new Book();
        book.name = name;
        return book;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
