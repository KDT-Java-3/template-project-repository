-- V3: Add order_status column to orders_products table
-- Add order_status column with default value 'PENDING'

ALTER TABLE orders_products
ADD COLUMN order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
