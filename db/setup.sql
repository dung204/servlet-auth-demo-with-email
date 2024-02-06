USE MASTER;

DROP DATABASE IF EXISTS AuthDemo;

CREATE DATABASE AuthDemo;

USE AuthDemo;

-- You can insert your own accounts, or go to sign up page to create
CREATE TABLE Accounts (
  id INT IDENTITY(1, 1) PRIMARY KEY,
  username NVARCHAR(255) NOT NULL,
  [password] NVARCHAR(255) NOT NULL,
  email NVARCHAR(255) NOT NULL,
);

CREATE TABLE OTPs (
  account_id INT NOT NULL,
  code NVARCHAR(255) NOT NULL,
  FOREIGN KEY (account_id) REFERENCES Accounts(id)
);