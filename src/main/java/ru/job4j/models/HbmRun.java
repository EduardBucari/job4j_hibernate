package ru.job4j.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            AutoModel model1 = AutoModel.of("2103");
            AutoModel model2 = AutoModel.of("2104");
            AutoModel model3 = AutoModel.of("2105");
            AutoModel model4 = AutoModel.of("2106");
            AutoModel model5 = AutoModel.of("2107");

            session.save(model1);
            session.save(model2);
            session.save(model3);
            session.save(model4);
            session.save(model5);

            AutoBrand vaz = AutoBrand.of("AUTOVAZZ");
            vaz.addModel(session.load(AutoModel.class, model1.getId()));
            vaz.addModel(session.load(AutoModel.class, model2.getId()));
            vaz.addModel(session.load(AutoModel.class, model3.getId()));
            vaz.addModel(session.load(AutoModel.class, model4.getId()));
            vaz.addModel(session.load(AutoModel.class, model5.getId()));

            session.save(vaz);
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}