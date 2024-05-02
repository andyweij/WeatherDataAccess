package com.eland.weatherData.DAO;

import com.eland.weatherData.model.TaskLogEntity;
import com.eland.weatherData.model.WeatherInfoEntity;
import com.eland.weatherData.model.testEntity;
import com.eland.weatherData.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatherInfoDAO {
    private static Logger logger = LogManager.getLogger(WeatherInfoDAO.class.getName());

    private SessionFactory mssqlSessionFactory = HibernateUtils.getMssqlSessionFactory();
    private SessionFactory mysqlSessionFactory = HibernateUtils.getMysqlSessionFactory();
    private Session session;
    private Transaction tx;


    public void insertTaskLog(TaskLogEntity taskLog) {
        try {
            session = mssqlSessionFactory.getCurrentSession();
            tx = session.beginTransaction();
            if (null != taskLog) {
                session.save(taskLog);
                tx.commit();
            }

        } catch (Exception e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
            tx.rollback();
        } finally {
            // 無論是否發生異常，都確保關閉 session
                session.close();
                logger.info("sessionClosed");
        }
    }

    public void insertWeatherData(List<WeatherInfoEntity> weatherInfoList) {
        try {
            session = mysqlSessionFactory.getCurrentSession();
            tx = session.beginTransaction();
            if (null != weatherInfoList) {
                for (WeatherInfoEntity weatherInfo : weatherInfoList) {
                    session.save(weatherInfo);
                }
                tx.commit();
            }
        } catch (Exception e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
            tx.rollback();
        } finally {
            // 無論是否發生異常，都確保關閉 session
                session.close();
                logger.info("sessionClosed");
        }
    }
    public void createtestEntity(List<testEntity> testEntity) {
        try {
            session = mysqlSessionFactory.getCurrentSession();
            tx = session.beginTransaction();
            if (null != testEntity) {
                for (testEntity weatherInfo : testEntity) {
                    session.save(weatherInfo);
                }
                tx.commit();
            }
        } catch (Exception e) {
            logger.info(e.getStackTrace());
            logger.info(e.getMessage());
            tx.rollback();
        } finally {
            // 無論是否發生異常，都確保關閉 session
                session.close();
                logger.info("sessionClosed");
        }
    }
}
