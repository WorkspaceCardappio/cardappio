CREATE TABLE IF NOT EXISTS product_order_additional (
   id UUID PRIMARY KEY,
   product_order_id UUID NOT NULL,
   product_item_id UUID NOT NULL,
   quantity DECIMAL(10,2) NOT NULL,

   CONSTRAINT fk_product_order_variable_product_order_id FOREIGN KEY (product_order_id) REFERENCES product_order(id),
   CONSTRAINT fk_product_order_variable_product_item_id FOREIGN KEY (product_item_id) REFERENCES product_item(id)
);