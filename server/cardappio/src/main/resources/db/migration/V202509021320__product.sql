CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity >= 0),
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expiration_date DATE,
    image VARCHAR(255),
    note VARCHAR(255),
    save_status VARCHAR(10) NOT NULL,
    category_id UUID NOT NULL,

    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);