CREATE TABLE user (
                         user_id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255),
                         phone_number VARCHAR(20),
                         password_hash varchar(255),
                         email varchar(255),
                         address varchar(255),
                         zip_code int (20),
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (user_id)
);