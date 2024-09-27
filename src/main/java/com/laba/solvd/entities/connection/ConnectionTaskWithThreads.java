package com.laba.solvd.entities.connection;

public class ConnectionTaskWithThreads {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();

        Thread runnableThread = new Thread(new ConnectionRunnable(pool));
        runnableThread.start();

        ConnectionThread thread = new ConnectionThread(pool);
        thread.start();

        try {
            runnableThread.join();
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted");
        }

        System.out.println("Both threads have finished executing.");
    }
}
