ALTER TABLE order add constraint fk_cust_order FOREIGN KEY (customerID) REFERENCES customer(customerID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE orderDesc add constraint fk_orderdesc_order FOREIGN KEY (orderID) REFERENCES order(orderID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE orderDesc add constraint fk_orderdesc_product FOREIGN KEY (productID) REFERENCES product(productID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE inventory add constraint fk_inventory_product FOREIGN KEY (productID) REFERENCES product(productID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE inventory add constraint fk_inventory_seller FOREIGN KEY (sellerID) REFERENCES seller(sellerID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE shipments add constraint fk_shipment_del FOREIGN KEY (delID) REFERENCES deliveryAgent(delID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE shipments add constraint fk_shipment_order FOREIGN KEY (delID) REFERENCES order(orderID) ON DELETE CASCADE ON UPDATE CASCADE;