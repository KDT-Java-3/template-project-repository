-- V2__add_parent_id_to_category.sql

ALTER TABLE category
    ADD COLUMN parent_id BIGINT NULL;