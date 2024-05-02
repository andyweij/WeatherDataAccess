package com.eland.weatherData.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory mssqlSessionFactory = buildMssqlSessionFactory();
	private static final SessionFactory mysqlSessionFactory = buildMysqlSessionFactory();

    public static SessionFactory getMssqlSessionFactory() {
        return mssqlSessionFactory;
    }
    public static SessionFactory getMysqlSessionFactory() {
        return mysqlSessionFactory;
    }

    private static SessionFactory buildMssqlSessionFactory() {
        // configure()讀取hibernate.cfg.xml
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
//      SessionFactory 是 Hibernate 的核心，它負責創建 Session 實例，用於與資料庫進行交互。
        return sessionFactory;
    }

    private static SessionFactory buildMysqlSessionFactory() {
        Configuration config = new Configuration().configure("mysql.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();

        return sessionFactory;
    }

}
