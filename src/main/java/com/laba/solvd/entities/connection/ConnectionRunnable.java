package com.laba.solvd.entities.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private final ConnectionPool pool;

    public ConnectionRunnable(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            Connection connection = pool.getConnection();
            connection.connect();
            Thread.sleep(1000);
            connection.disconnect();
            pool.releaseConnection(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted: " + Thread.currentThread().getName());
        }
    }
}
