package com.laba.solvd.entities.order;

import com.laba.solvd.entities.CustomLinkedList;
import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.payments.OrderStatus;
import com.laba.solvd.entities.people.Customer;
import com.laba.solvd.entities.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Order {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);
    private String orderId;
    private Customer customer;
    private LocalDate orderDate;
    private CustomLinkedList<OrderItem> orderItems;
    private static double totalExpenses;
    private Vehicle vehicle;
    private OrderStatus orderStatus = OrderStatus.PENDING;

    public Order(String orderId, LocalDate orderDate, Customer customer, Vehicle vehicle) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.vehicle = vehicle;
        this.orderItems = new CustomLinkedList<>();
        logger.info("Order created with ID: {}. Initial status: {}", orderId, orderStatus.getDisplayName());
    }

    private void validateOrder() {
        if (!isValidOrder(this)) {
            logger.error("Order validation failed for OrderId: {}", orderId);
            throw new InvalidDataException("Invalid order details: " + this);
        }
        logger.info("Order validation successful for OrderId: {}", orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        logger.info("OrderId updated to: {}", orderId);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        logger.info("Customer updated to: {}", customer);
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        logger.info("OrderDate updated to: {}", orderDate);
    }

    public void setOrderItems(CustomLinkedList<OrderItem> orderItems) {
        this.orderItems = orderItems;
        logger.info("OrderItems updated.");
    }

    public static double getTotalExpenses() {
        return totalExpenses;
    }

    public static void setTotalExpenses(double totalExpenses) {
        Order.totalExpenses = totalExpenses;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
    }

    public CustomLinkedList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double calculateTotalItemsCost() {
        return orderItems.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }

    public static boolean isValidOrder(Order order) {
        boolean isValid = order.getOrderId() != null && !order.getOrderId().trim().isEmpty()
                && order.getOrderDate() != null && order.getCustomer() != null
                && order.getVehicle() != null;

        if (!isValid) {
            logger.warn("Order validation failed: {}", order);
        }

        return isValid;
    }

    public static OrderItem findMostExpensiveItem(Order order) {
        return order.getOrderItems().stream()
                .max(Comparator.comparingDouble(OrderItem::getPrice))
                .map(item -> {
                    logger.info("Most expensive item in Order ID: " + order.getOrderId() + " is " +
                            item.getName() + " with price $" + item.getPrice());
                    return item;
                })
                .orElse(null);
    }

    @Override
    public String toString() {
        return "OrderId " + orderId +
                ", customer: " + customer +
                ", orderDate: " + orderDate +
                ", orderItems: " + orderItems +
                ", vehicle: " + vehicle;
    }
}
