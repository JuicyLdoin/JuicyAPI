package net.juicy.api.utils.util;

import net.juicy.api.server.JuicyServer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

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

    public static <T> Optional<T> getAndCallAction(Class<T> entityType, @Nullable Object object, Consumer<T> consumer) {

        Optional<T> receivedOptional = get(entityType, object);
        receivedOptional.ifPresent(consumer);

        return receivedOptional;

    }

    public static <T> Optional<T> get(Class<T> entityType, @Nullable Object object) {

        if (object != null) {

            Session session = getSessionFactory().openSession();
            T received = session.get(entityType, object);

            return Optional.ofNullable(received);

        } else
            return Optional.empty();

    }

    public static Optional<Query<Object>> createQuery(String queryString) {

        return createQuery(queryString, Object.class);

    }

    public static <T> Optional<Query<T>> createQuery(String queryString, Class<T> resultClass) {

        return Optional.ofNullable(getSessionFactory().openSession().createQuery(queryString, resultClass).setCacheable(false));

    }

    public static <T> Optional<Query<T>> createQueryAndCallActionForEach(String queryString, Class<T> resultClass, Consumer<T> consumer) {

        Optional<Query<T>> optional = Optional.ofNullable(getSessionFactory().openSession().createQuery(queryString, resultClass).setCacheable(false));
        optional.ifPresent(query -> query.list().forEach(consumer));

        return optional;

    }

    public static void saveOrUpdate(Object object) {

        if (object != null) {

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(object);

            transaction.commit();

        }
    }
}