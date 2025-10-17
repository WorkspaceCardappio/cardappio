CREATE TABLE IF NOT EXISTS product_item (
   id UUID PRIMARY KEY,
   product_id UUID NOT NULL,
   price DECIMAL(10,2) NOT NULL,
   quantity DECIMAL(10,2) NOT NULL,
   size_item NUMERIC(10),
   active BOOLEAN NOT NULL DEFAULT TRUE,

   CONSTRAINT fk_product_item_product_id FOREIGN KEY (product_id) REFERENCES product(id),
   CONSTRAINT fk_product_item_size_item FOREIGN KEY (size_item) REFERENCES item_status(code)
);