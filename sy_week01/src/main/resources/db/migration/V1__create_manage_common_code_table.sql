CREATE TABLE manage_common_code (
         column_name        VARCHAR(100),
         code               VARCHAR(255),
         code_description   VARCHAR(255),
         first_create_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
         modify_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
         PRIMARY KEY (column_name, code)
);