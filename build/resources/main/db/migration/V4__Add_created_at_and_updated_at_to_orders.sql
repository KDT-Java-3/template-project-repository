-- V4: Add created_at and updated_at columns to orders table
-- Add timestamp columns for order creation and update tracking

ALTER TABLE orders
ADD COLUMN created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE orders
ADD COLUMN updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
