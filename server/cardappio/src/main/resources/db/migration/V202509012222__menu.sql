CREATE TABLE IF NOT EXISTS menu (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    note VARCHAR(255),
    restaurant_id UUID NOT NULL,
    CONSTRAINT fk_menu_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);
