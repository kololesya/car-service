package com.laba.solvd.entities.order;

import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.exceptions.OrderProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);

    private Map<String, Integer> partsInventory;

    public Warehouse() {
        partsInventory = new HashMap<>();
        logger.info("Warehouse initialized.");
    }

    public void addParts(String partName, int quantity) {
        if (quantity <= 0) {
            logger.error("Invalid quantity: {}. Quantity must be greater than zero.", quantity);
            throw new InvalidDataException("Quantity must be greater than zero.");
        }

        partsInventory.put(partName, partsInventory.getOrDefault(partName, 0) + quantity);
        logger.info("Added {} units of {} to the warehouse.", quantity, partName);
    }

    public boolean checkPartAvailability(String partName, int requiredQuantity) {
        int availableQuantity = partsInventory.get(partName);
        return availableQuantity >= requiredQuantity;
    }

    public void issueParts(String partName, int quantity) {
        if (!checkPartAvailability(partName, quantity)) {
            logger.error("Not enough {} in the warehouse. Requested: {}, Available: {}", partName, quantity, partsInventory.getOrDefault(partName, 0));
            throw new OrderProcessingException("Not enough " + partName + " in the warehouse.");
        }

        partsInventory.put(partName, partsInventory.get(partName) - quantity);
    }

    public void printInventory() {
        logger.info("Current warehouse inventory:");
        for (Map.Entry<String, Integer> entry : partsInventory.entrySet()) {
            System.out.println("Part: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void processOrder(Order order) {
        logger.info("Processing order ID: {} for customer: {}", order.getOrderId(), order.getCustomer().getName());

        for (OrderItem item : order.getOrderItems()) {
            String partName = item.getName();
            int requiredQuantity = item.getQuantity();

            if (checkPartAvailability(partName, requiredQuantity)) {
                issueParts(partName, requiredQuantity);
                logger.info("Processed {} units of '{}' from order. Remaining inventory: {}", requiredQuantity, partName, partsInventory.get(partName));
            } else {
                logger.warn("Not enough '{}' in the warehouse to fulfill the order. Required: {}, Available: {}", partName, requiredQuantity, partsInventory.getOrDefault(partName, 0));
            }
        }

        logger.info("Order ID: {} processed successfully.", order.getOrderId());
    }

}
