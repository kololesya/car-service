package com.laba.solvd.entities.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class ConnectionTask {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionTask.class);
    private final ConnectionPool pool;

    public ConnectionTask(ConnectionPool pool) {
        this.pool = pool;
    }

    public CompletableFuture<Void> getConnectionAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                Connection connection = pool.getConnection();
                logger.info("Connection acquired: " + connection.getId());
                connection.connect();
                Thread.sleep(1000);
                connection.disconnect();
                logger.info("Connection released: " + connection.getId());
                pool.releaseConnection(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted", e);
            }
        });
    }
}
