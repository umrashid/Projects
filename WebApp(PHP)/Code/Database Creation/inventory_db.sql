-- Create the my_book_collection database
DROP DATABASE IF EXISTS inventory_database;
CREATE DATABASE inventory_database;
USE inventory_database;  -- MySQL command

-- create the tables
CREATE TABLE Product (
  productID       INT(11)        NOT NULL   UNIQUE,
  Description     VARCHAR(255)   NOT NULL,
  Price     varchar(10)   NOT NULL,
  Weight INT(5) NOT NULL,
  PRIMARY KEY (productID)    
);



CREATE TABLE Stores (
  storeID       INT(4)        NOT NULL   UNIQUE,
  Street_Number     VARCHAR(255)   NOT NULL,
  Street_Name     VARCHAR(255)   NOT NULL,
  City     VARCHAR(255)   NOT NULL,
  Province varchar(255) NOT NULL,
  Phone_Number VARCHAR(255),
  PRIMARY KEY (storeID)
);

CREATE TABLE Inventory (
  
  productID       INT(11) NOT NULL,
  storeID       INT(11)        NOT NULL,
  Quantity INT(11) , 
  PRIMARY KEY (productID,storeID)
);

Create Table userInfo (
  username INT(4) NOT NULL,
  passwordLogin VARCHAR(255) NOT NULL,
  PRIMARY KEY (username)
);

-- create the users and grant priveleges to those users
GRANT SELECT, INSERT, DELETE, UPDATE
ON inventory_database.*
TO user@localhost
IDENTIFIED BY 'password';

