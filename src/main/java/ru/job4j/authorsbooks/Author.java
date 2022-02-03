package ru.job4j.authorsbooks;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public static Author of(String name) {
        Author author = new Author();
        author.name = name;
        return author;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return id == author.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
