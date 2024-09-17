package com.laba.solvd.entities.order;

import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.exceptions.OrderProcessingException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Warehouse {
    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);
    private static final String INVENTORY_FILE_PATH = "target/inventory.xlsx";

    private Map<String, Integer> partsInventory;
    private Set<String> inventoryLogEntries;

    public Warehouse() {
        partsInventory = new HashMap<>();
        inventoryLogEntries = new HashSet<>();
        logger.info("Warehouse initialized.");
        loadInventory();
    }

    public void addParts(String partName, int quantity) {
        try {
            if (quantity <= 0) {
                throw new InvalidDataException("Quantity must be greater than zero.");
            }

            partsInventory.put(partName, partsInventory.getOrDefault(partName, 0) + quantity);
            String logEntry = generateInventoryLogEntry(partName, quantity);

            if (inventoryLogEntries.add(logEntry)) {
                writeInventoryToFile();
            }

            logger.info("Added {} units of {} to the warehouse.", quantity, partName);
        } catch (InvalidDataException e) {
            logger.error("Error adding parts: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding parts.", e);
        }
    }

    public boolean checkPartAvailability(String partName, int requiredQuantity) {
        int availableQuantity = partsInventory.get(partName);
        return availableQuantity >= requiredQuantity;
    }

    public void issueParts(String partName, int quantity) {
        try {
            if (!checkPartAvailability(partName, quantity)) {
                throw new OrderProcessingException("Not enough " + partName + " in the warehouse.");
            }

            partsInventory.put(partName, partsInventory.get(partName) - quantity);
            String logEntry = generateInventoryLogEntry(partName, -quantity);

            if (inventoryLogEntries.add(logEntry)) {
                writeInventoryToFile();
            }

            logger.info("Processed {} units of '{}' from order. Remaining inventory: {}", quantity, partName, partsInventory.get(partName));
        } catch (OrderProcessingException e) {
            logger.error("Error issuing parts: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while issuing parts.", e);
        }
    }

    public void printInventory() {
        try {
            logger.info("Current warehouse inventory:");
            for (Map.Entry<String, Integer> entry : partsInventory.entrySet()) {
                System.out.println("Part: " + entry.getKey() + ", Quantity: " + entry.getValue());
            }
        } catch (Exception e) {
            logger.error("Error printing inventory.", e);
        }
    }

    public void processOrder(Order order) {
        try {
            logger.info("Processing order ID: {} for customer: {}", order.getOrderId(), order.getCustomer().getName());

            for (OrderItem item : order.getOrderItems()) {
                String partName = item.getName();
                int requiredQuantity = item.getQuantity();

                if (checkPartAvailability(partName, requiredQuantity)) {
                    issueParts(partName, requiredQuantity);
                    logger.info("Processed {} units of '{}' from order. Remaining inventory: {}", requiredQuantity,
                            partName, partsInventory.get(partName));
                } else {
                    logger.warn("Not enough '{}' in the warehouse to fulfill the order. Required: {}, Available: {}",
                            partName, requiredQuantity, partsInventory.getOrDefault(partName, 0));
                }
            }

            logger.info("Order ID: {} processed successfully.", order.getOrderId());
        } catch (Exception e) {
            logger.error("Error processing order: {}", e.getMessage());
        }
    }

    private void loadInventory() {
        File file = new File(INVENTORY_FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    Cell partNameCell = row.getCell(0);
                    Cell quantityCell = row.getCell(1);

                    if (partNameCell != null && quantityCell != null) {
                        String partName = partNameCell.getStringCellValue().trim();

                        int quantity;
                        if (quantityCell.getCellType() == CellType.NUMERIC) {
                            quantity = (int) quantityCell.getNumericCellValue();
                        } else if (quantityCell.getCellType() == CellType.STRING) {
                            try {
                                quantity = Integer.parseInt(quantityCell.getStringCellValue().trim());
                            } catch (NumberFormatException e) {
                                logger.warn("Quantity in cell is not a valid number: {}", quantityCell.getStringCellValue());
                                continue;
                            }
                        } else {
                            logger.warn("Unexpected cell type for quantity: {}", quantityCell.getCellType());
                            continue;
                        }

                        partsInventory.put(partName, partsInventory.getOrDefault(partName, 0) + quantity);
                        inventoryLogEntries.add(generateInventoryLogEntry(partName, quantity));
                    }
                }
                logger.info("Existing inventory loaded from file.");
            } catch (IOException e) {
                logger.error("Failed to read existing inventory from file.", e);
            }
        } else {
            logger.info("Inventory file does not exist, starting with an empty inventory.");
        }
    }

    private String generateInventoryLogEntry(String partName, int quantity) {
        return String.format("%s | %d", partName, quantity);
    }

    private void writeInventoryToFile() {
        File file = new File(INVENTORY_FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(file);
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Inventory");
            int rowIndex = 0;

            Row headerRow = sheet.createRow(rowIndex++);
            headerRow.createCell(0).setCellValue("Part Name");
            headerRow.createCell(1).setCellValue("Quantity");

            for (Map.Entry<String, Integer> entry : partsInventory.entrySet()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            workbook.write(fos);
        } catch (IOException e) {
            logger.error("Failed to write inventory to file.", e);
        }
    }
}
