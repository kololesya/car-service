package com.laba.solvd.entities.payments;

import com.laba.solvd.entities.exceptions.PaymentProcessingException;
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
        try {
            if (this.serviceCost == null) {
                throw new PaymentProcessingException("Cannot process payment: Service cost is null.");
            }

            double totalAmount = this.serviceCost.calculateCost();
            String orderId = this.serviceCost.getOrder().getOrderId();;

            logger.info("Processing payment for " + orderId + " | Total Amount: $" + totalAmount);
            logger.info("Payment of $" + totalAmount + " has been successfully processed for: " + orderId);
        } catch (PaymentProcessingException e) {
            logger.error("Payment processing failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void processRefund() {
        try {
            if (this.serviceCost == null) {
                throw new PaymentProcessingException("Cannot process refund: Service cost is null.");
            }

            double totalAmount = this.serviceCost.calculateCost();
            String orderId = this.serviceCost.getOrder().getOrderId();

            logger.info("Processing refund for Order ID: " + orderId + " | Total Amount: $" + totalAmount);
            logger.info("Refund of $" + totalAmount + " has been successfully processed for Order ID: " + orderId);
        } catch (PaymentProcessingException e) {
            logger.error("Refund processing failed: " + e.getMessage(), e);
        }
    }
}
