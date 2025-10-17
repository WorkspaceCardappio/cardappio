CREATE TABLE IF NOT EXISTS product_item_ingredient (
    id UUID PRIMARY KEY,
    product_item_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity >= 0),

    CONSTRAINT fk_product_item_ingredient_product_item_id FOREIGN KEY (product_item_id) REFERENCES product_item(id),
    CONSTRAINT fk_product_item_ingredient_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);
