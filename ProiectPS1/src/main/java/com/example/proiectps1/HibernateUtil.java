package com.example.proiectps1;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Constructor privat pentru a împiedica instanțierea directă
    private HibernateUtil() {
    }

    // Metodă statică pentru a obține instanța unică de SessionFactory
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // Configurarea sessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    // Metodă pentru închiderea SessionFactory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
