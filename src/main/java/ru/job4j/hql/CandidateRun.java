package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class CandidateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        Candidate result = null;
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /**
            Сохраните 3 объекта в таблице, данные возьмите произвольно.
            Candidate first = Candidate.of("Viktor", 10, 100500);
            Candidate second = Candidate.of("Ivan", 24, 17500);
            Candidate third = Candidate.of("Stepan", 10, 32000);
            session.save(first);
            session.save(second);
            session.save(third);
            */

            /**
            Выполните запросы выборки всех кандидатов
            Query query = session.createQuery("from Candidate");
            for (Object arg : query.list()) {
                System.out.println(arg);
            }
            */
            /**
            кандидата по id
            Query query = session.createQuery("from Candidate c where c.id = 2");
            System.out.println(query.uniqueResult());
            */
            /**
            кандидата по имени
            Query query = session.createQuery("from Candidate c where c.name =: newName")
                    .setParameter("newName", "Viktor");
            System.out.println(query.uniqueResult());
            */
            /**
            обновления записи кандидата
            session.createQuery("update Candidate c set c.name =: newName where c.id =: fId")
                    .setParameter("newName", "Vlad")
                    .setParameter("fId", 1)
                    .executeUpdate();
            */
            /**
            удаления записи кандидата по id
            session.createQuery("delete from Candidate c where c.id =: fId")
                    .setParameter("fId", 1).executeUpdate();
            */
            /**
             Vacancy vacancy1 = Vacancy.of("Java_Developer");
             Vacancy vacancy2 = Vacancy.of("CPP_Developer");
             Vacancy vacancy3 = Vacancy.of("Puthon_Developer");
             session.save(vacancy1);
             session.save(vacancy2);
             session.save(vacancy3);
             DataVacancies dataVacancies = DataVacancies.of("Developers");
             dataVacancies.addVacancy(vacancy1);
             dataVacancies.addVacancy(vacancy2);
             dataVacancies.addVacancy(vacancy3);
             session.save(dataVacancies);
             Candidate candidate = Candidate.of("Oksana", 1, 180000);
             candidate.setDataVacancies(dataVacancies);
             session.save(candidate);
             */

            result = (Candidate) session.createQuery(
                            "select distinct c from Candidate c "
                                    + " join fetch c.dataVacancies d"
                                    + " join fetch d.vacancyList v where c.id =: fId")
                    .setParameter("fId", 5)
                    .uniqueResult();
            System.out.println(result);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
