ALTER TABLE product ADD CONSTRAINT uk_product_name UNIQUE (name);
ALTER TABLE category ADD CONSTRAINT uk_category_name UNIQUE (name);