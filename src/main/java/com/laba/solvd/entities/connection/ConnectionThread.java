package com.laba.solvd.entities.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionThread extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private final ConnectionPool pool;

    public ConnectionThread(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            logger.info("Thread " + Thread.currentThread().getName() + " is trying to get a connection.");
            Connection connection = pool.getConnection();
            logger.info("Thread " + Thread.currentThread().getName() + " obtained connection: " + connection.getId());
            connection.connect();
            Thread.sleep((long) (Math.random() * 3000));
            connection.disconnect();
            pool.releaseConnection(connection);
            logger.info("Thread " + Thread.currentThread().getName() + " released the connection.");
        } catch (InterruptedException e) {
            logger.warn("Thread " + Thread.currentThread().getName() + " was interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Thread " + Thread.currentThread().getName() + " encountered an error: " + e.getMessage());
        }
    }
}
