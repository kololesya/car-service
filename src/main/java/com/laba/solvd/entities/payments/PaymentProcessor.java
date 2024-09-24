package com.laba.solvd.entities.payments;

import com.laba.solvd.entities.exceptions.PaymentProcessingException;
import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.service.ServiceCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentProcessor implements PaymentProcessing{
    private static final Logger logger = LoggerFactory.getLogger(Department.class);
    private static final String PAYMENT_LOG_PATH = "target/payment-log.txt";
    private ServiceCost serviceCost;
    private Set<String> uniqueLogs = new HashSet<>();

    public PaymentProcessor(ServiceCost serviceCost) {

        this.serviceCost = serviceCost;
        loadExistingLogs();
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
            String orderId = this.serviceCost.getOrder().getOrderId();
            OrderStatus newStatus = OrderStatus.PAID;

            if (StringUtils.isBlank(orderId)) {
                throw new PaymentProcessingException("Order ID is blank.");
            }

            logger.info("Processing payment for " + orderId + " | Total Amount: $" + totalAmount);
            logger.info("Payment of $" + totalAmount + " has been successfully processed for: " + orderId);

            String formattedOrderId = StringUtils.trim(orderId);
            String logEntry = generateLogEntry("Payment", formattedOrderId, totalAmount, newStatus.getDisplayName());

            if (uniqueLogs.add(logEntry)) {
                writeToFile(logEntry, PAYMENT_LOG_PATH);
                logger.info("Log entry added: " + logEntry);
            } else {
                logger.info("Log entry already exists: " + logEntry);
            }
            this.serviceCost.getOrder().setOrderStatus(newStatus);
            logger.info("Order status updated to: " + newStatus.getDisplayName());

            logger.info(logEntry);
        } catch (PaymentProcessingException e) {
            logger.error("Payment processing failed: " + e.getMessage(), e);
            this.serviceCost.getOrder().setOrderStatus(OrderStatus.CANCELED);
            logger.info("Order status updated to: " + OrderStatus.CANCELED.getDisplayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            OrderStatus newStatus = OrderStatus.RETURNED;

            logger.info("Processing refund for Order ID: " + orderId + " | Total Amount: $" + totalAmount);
            logger.info("Refund of $" + totalAmount + " has been successfully processed for Order ID: " + orderId);

            this.serviceCost.getOrder().setOrderStatus(newStatus);
            logger.info("Order status updated to: " + newStatus.getDisplayName());

            String logEntry = generateLogEntry("Refund", orderId, totalAmount, newStatus.getDisplayName());
            writeToFile(logEntry, PAYMENT_LOG_PATH);

            logger.info(logEntry);
        } catch (PaymentProcessingException e) {
            logger.error("Refund processing failed: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateLogEntry(String type, String orderId, double amount, String status) {
        String customerName = serviceCost.getOrder().getCustomer().getName();
        return StringUtils.join(new String[]{type, orderId, String.format("$%.2f", amount), customerName,
                status}, " | ");
    }

    private void writeToFile(String content, String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, content + System.lineSeparator(), StandardCharsets.UTF_8, true);
    }

    private void loadExistingLogs() {
        File file = new File(PAYMENT_LOG_PATH);
        if (file.exists()) {
            try {
                uniqueLogs.addAll(
                        Files.lines(file.toPath())
                                .map(String::trim)
                                .collect(Collectors.toSet())
                );
                logger.info("Existing logs loaded from file.");
            } catch (IOException e) {
                logger.error("Failed to read existing logs from file.", e);
            }
        } else {
            logger.info("Log file does not exist, starting with an empty log set.");
        }
    }
}
