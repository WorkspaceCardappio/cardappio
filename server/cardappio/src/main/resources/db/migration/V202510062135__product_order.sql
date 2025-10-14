CREATE TABLE IF NOT EXISTS product_order (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,

    CONSTRAINT fk_order
    FOREIGN KEY (order_id)
    REFERENCES client_order (id),

    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES product (id)
    );