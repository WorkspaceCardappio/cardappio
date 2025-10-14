CREATE TABLE IF NOT EXISTS additional(
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    note VARCHAR(255),

    CONSTRAINT fk_additional_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);