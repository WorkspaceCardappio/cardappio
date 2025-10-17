CREATE TABLE IF NOT EXISTS product_order (
   id UUID PRIMARY KEY,
   client_order_id UUID NOT NULL,
   product_id UUID NOT NULL,
   price DECIMAL(10,2) NOT NULL,
   quantity DECIMAL(10,2) NOT NULL,
   total DECIMAL(10,2) NOT NULL,
   note VARCHAR(255),

   CONSTRAINT fk_product_order_client_order_id FOREIGN KEY (client_order_id) REFERENCES client_order(id),
   CONSTRAINT fk_product_order_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);