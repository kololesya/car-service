package com.laba.solvd.entities.payments;

import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.service.ServiceCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentProcessor implements PaymentProcessing{
    private static final Logger logger = LoggerFactory.getLogger(Department.class);
    private ServiceCost serviceCost;

    public PaymentProcessor(ServiceCost serviceCost) {
        this.serviceCost = serviceCost;
    }

    public ServiceCost getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(ServiceCost serviceCost) {
        this.serviceCost = serviceCost;
    }

    @Override
    public void processPayment() {
        if (this.serviceCost == null) {
            logger.error("Cannot process payment: Service cost is null.");
            return;
        }

        double totalAmount = this.serviceCost.calculateCost();
        String orderId = generateOrderId();

        logger.info("Processing payment for" + orderId + " | Total Amount: $" + totalAmount);
        logger.info("Payment of $" + totalAmount + " has been successfully processed for: " + orderId);
    }

    @Override
    public void processRefund() {
        if (this.serviceCost == null) {
            logger.error("Cannot process refund: Service cost is null.");
            return;
        }

        double totalAmount = this.serviceCost.calculateCost();
        String orderId = generateOrderId();

        logger.info("Processing refund for Order ID: " + orderId + " | Total Amount: $" + totalAmount);
        logger.info("Refund of $" + totalAmount + " has been successfully processed for Order ID: " + orderId);
    }

    private String generateOrderId() {
        if (serviceCost == null || serviceCost.getCar() == null || serviceCost.getServiceName() == null) {
            logger.warn("Unable to generate Order ID: Missing service cost, car, or service name.");
            return "Unknown";
        }

        StringBuilder orderIdBuilder = new StringBuilder();
        orderIdBuilder.append(serviceCost.getServiceName());
        orderIdBuilder.append(" for the car with ");
        orderIdBuilder.append(serviceCost.getCar().getVinNumber());

        return orderIdBuilder.toString();
    }
}
