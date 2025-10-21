CREATE TABLE IF NOT EXISTS product_ingredient (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,

    CONSTRAINT fk_product_ingredient_product_id FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_product_ingredient_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);
