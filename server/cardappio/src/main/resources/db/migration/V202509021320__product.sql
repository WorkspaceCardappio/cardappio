CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity >= 0),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expiration_date DATE NOT NULL,
    image VARCHAR(255),
    category_id UUID,
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);