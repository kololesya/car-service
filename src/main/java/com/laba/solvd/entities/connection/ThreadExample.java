package com.laba.solvd.entities.connection;

public class ThreadExample {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();

        Thread thread1 = new Thread(new MyRunnableTask(pool));
        thread1.setName("RunnableThread-1");

        MyThreadTask thread2 = new MyThreadTask(pool);
        thread2.setName("ThreadTask-1");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Both threads have finished executing.");
    }
}
