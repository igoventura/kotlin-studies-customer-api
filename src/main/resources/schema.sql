CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);