CREATE TABLE IF NOT EXISTS product_variable (
   id UUID PRIMARY KEY,
   product_id UUID NOT NULL,
   name VARCHAR(255) NOT NULL,
   price DECIMAL(10,2) NOT NULL,
   active BOOLEAN NOT NULL DEFAULT TRUE,

   CONSTRAINT fk_additional_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);