CREATE TABLE `user` (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                        updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                        PRIMARY KEY (id),
                        UNIQUE KEY uk_user_email (email)
);
