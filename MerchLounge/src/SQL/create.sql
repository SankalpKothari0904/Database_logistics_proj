create table customer(
    cusomterID INTEGER PRIMARY KEY,
    customerName VARCHAR(25),
    address VARCHAR(25),
    phNo VARCHAR(25)
);
create table product(
    productID INTEGER PRIMARY KEY,
    productName VARCHAR(25),
    price DECIMAL(8,2),
);
create table deliveryAgent(
    delID INTEGER PRIMARY KEY,
    phNo VARCHAR(25)
);
create table seller(
    sellerID INTEGER PRIMARY KEY,
    sellerName VARCHAR(25),
    address VARCHAR(25),
    phNo VARCHAR(25)
);
create table order(
    orderID INTEGER PRIMARY KEY,
    customerID INTEGER,
    delDate DATE
);
create table orderDesc(
    slNo INTEGER PRIMARY KEY,
    productID INTEGER,
    orderID INTEGER,
    quantity INTEGER,
    type CHAR(1),
    status VARCHAR(25)
);
create table shipments(
    slNo INTEGER PRIMARY KEY,
    delID INTEGER,
    orderID INTEGER,
    mode_of_shipment VARCHAR(25)
);
create table inventory(
    slNo INTEGER PRIMARY KEY,
    sellerID INTEGER,
    productID INTEGER
    quantity INTEGER,
)