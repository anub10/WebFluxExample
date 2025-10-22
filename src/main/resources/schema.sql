CREATE TABLE accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  balance DECIMAL(19,2)
);

CREATE TABLE transactions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  from_account_id BIGINT,
  to_account_id BIGINT,
  amount DECIMAL(19,2),
  timestamp TIMESTAMP
);

INSERT INTO accounts (name, balance) VALUES ('Tom', 1000.00);
INSERT INTO accounts (name, balance) VALUES ('Pom', 500.00);
