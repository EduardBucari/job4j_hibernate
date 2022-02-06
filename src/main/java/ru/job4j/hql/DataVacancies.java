package ru.job4j.hql;

import net.bytebuddy.matcher.FilterableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "data_vacancies")
public class DataVacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancyList = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        this.vacancyList.add(vacancy);
    }

    public static DataVacancies of(String name) {
        DataVacancies dataVacancies = new DataVacancies();
        dataVacancies.name = name;
        return dataVacancies;
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

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataVacancies that = (DataVacancies) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataVacancies{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", vacancyList=" + vacancyList
                + '}';
    }
}
