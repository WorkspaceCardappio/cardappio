CREATE TABLE IF NOT EXISTS product_order_additional (
   id UUID PRIMARY KEY,
   product_order_id UUID NOT NULL,
   additional_id UUID NOT NULL,
   quantity DECIMAL(10,2) NOT NULL,

   CONSTRAINT fk_product_order_additional_product_order_id FOREIGN KEY (product_order_id) REFERENCES product_order(id),
   CONSTRAINT fk_product_order_additional_additional_id FOREIGN KEY (additional_id) REFERENCES additional(id)
);