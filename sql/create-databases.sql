-- Create databases ----------------------------------------------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS usermanagement;
CREATE DATABASE IF NOT EXISTS categories;
CREATE DATABASE IF NOT EXISTS products;

-- create root user and grant rights
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

-- Create users --------------------------------------------------------------------------------------------------------

-- create separate users for each microservice
CREATE USER 'usermanagementuser'@'%' IDENTIFIED BY 'password';
CREATE USER 'categoriesuser'@'%' IDENTIFIED BY 'password';
CREATE USER 'productsuser'@'%' IDENTIFIED BY 'password';
-- todo: extract passwords somehow

-- grant necessary privileges to users
GRANT ALL PRIVILEGES ON usermanagement.* TO 'usermanagementuser'@'%';
GRANT ALL PRIVILEGES ON categories.* TO 'categoriesuser'@'%';
GRANT ALL PRIVILEGES ON products.* TO 'productsuser'@'%';

-- db: usermanagement --------------------------------------------------------------------------------------------------

USE usermanagement;

CREATE TABLE role
(
    id     INT NOT NULL AUTO_INCREMENT,
    level1 INT,
    type   VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE customer
(
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    role     INT          NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_mufchskagt7e1w4ksmt9lum5l ON customer (username ASC);

CREATE INDEX FK74aoh99stptslhotgf41fitt0 ON customer (role ASC);

-- db: categories ------------------------------------------------------------------------------------------------------

USE categories;

CREATE TABLE category
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- db: products --------------------------------------------------------------------------------------------------------

USE products;

CREATE TABLE product
(
    id          INT NOT NULL AUTO_INCREMENT,
    details     VARCHAR(255),
    name        VARCHAR(255),
    price       DOUBLE,
    category_id INT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX FK1mtsbur82frn64de7balymq9s ON product (category_id ASC);
