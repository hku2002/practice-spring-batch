CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderName VARCHAR(200) NOT NULL,
    price INT NOT NULL,
    orderDateTime DATETIME NOT NULL DEFAULT now()
);

CREATE TABLE settlements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderName VARCHAR(200)  NOT NULL,
    price INT  NOT NULL,
    orderDateTime DATETIME  NOT NULL,
    settlementDateTime DATETIME  NOT NULL DEFAULT now()
);
