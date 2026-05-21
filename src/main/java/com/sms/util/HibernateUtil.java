package com.sms.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private HibernateUtil() {}
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                System.out.println("Hibernate SessionFactory created successfully.");

            } catch (Exception e) {
                System.err.println("Failed to create SessionFactory: " + e.getMessage());
                throw new RuntimeException("SessionFactory creation failed.", e);
            }
        }
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory closed.");
        }
    }
}
