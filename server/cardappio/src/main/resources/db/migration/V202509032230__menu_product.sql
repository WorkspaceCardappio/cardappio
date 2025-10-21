CREATE TABLE IF NOT EXISTS menu_product (
    id UUID PRIMARY KEY,
    menu_id UUID NOT NULL,
    product_id UUID NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_menu_product_menu_id FOREIGN KEY (menu_id) REFERENCES menu(id),
    CONSTRAINT fk_menu_product_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);
