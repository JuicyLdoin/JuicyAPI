package net.juicy.api.utils.util;

import net.juicy.api.server.JuicyServer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateUtil {

    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null)
            try {

                configuration = new Configuration().configure()
                        .addAnnotatedClass(JuicyServer.class);

                sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

            } catch (Exception exception) {

                exception.printStackTrace();

            }

        return sessionFactory;
    }
    
    public static void registerAnnotatedClass(Class<?> clazz) {

        if (sessionFactory == null)
            getSessionFactory();

        configuration.addAnnotatedClass(clazz);
        sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

    }

    public static <T> T get(Class<T> entityType, Object id) {

        Session session = getSessionFactory().openSession();

        T object = session.get(entityType, id);

        session.close();

        return object;

    }

    public static Query createQuery(String queryString) {

        return getSessionFactory().openSession().createQuery(queryString);

    }

    public static <R> Query<R> createQuery(String queryString, Class<R> resultClass) {

        return getSessionFactory().openSession().createQuery(queryString, resultClass);

    }

    public static void saveOrUpdate(Object object) {

        if (object == null)
            return;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(object);

        transaction.commit();

        session.close();

    }
}