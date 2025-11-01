create table users(
     id BIGINT NOT NULL AUTO_INCREMENT,
     username varchar(255),
     email varchar(255),
     password_hash varchar(255),
     created_at timestamp,
     updated_at timestamp,
     PRIMARY KEY (id)
);