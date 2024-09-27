package com.laba.solvd.entities.connection;

public class MyRunnableTask implements Runnable{
    private final ConnectionPool pool;

    public MyRunnableTask(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " is trying to get a connection.");
            Connection connection = pool.getConnection();

            System.out.println("Thread " + Thread.currentThread().getName() + " obtained connection: " + connection.getId());
            connection.connect();

            Thread.sleep((long) (Math.random() * 3000));

            connection.disconnect();
            pool.releaseConnection(connection);
            System.out.println("Thread " + Thread.currentThread().getName() + " released the connection.");

        } catch (InterruptedException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Thread " + Thread.currentThread().getName() + " encountered an error: " + e.getMessage());
        }
    }
}
