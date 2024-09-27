package com.laba.solvd.entities.connection;

import com.laba.solvd.entities.people.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection {
    private final int id;
    private static final Logger logger = LoggerFactory.getLogger(Department.class);

    public Connection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void connect() {
        logger.info("Connection " + id + " is in use.");
    }

    public void disconnect() {
        logger.info("Connection " + id + " is released.");
    }
}
