CREATE TABLE IF NOT EXISTS ingredient_stock (
    id UUID PRIMARY KEY,
    ingredient_id UUID NOT NULL,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity >= 0),
    expiration_date DATE NOT NULL,
    number numeric(18, 0) NOT NULL,

    CONSTRAINT fk_ingredient_batch_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);
