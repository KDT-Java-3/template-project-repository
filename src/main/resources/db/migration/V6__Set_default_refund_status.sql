-- V6: Set default value for refund_status column to PENDING

ALTER TABLE refunds
ALTER COLUMN refund_status SET DEFAULT 'PENDING';
