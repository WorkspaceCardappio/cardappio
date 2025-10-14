CREATE TABLE IF NOT EXISTS table_restaurant (
    id UUID PRIMARY KEY,
    number VARCHAR(10) NOT NULL UNIQUE,
    places NUMERIC(10) NOT NULL,
    status NUMERIC(10),
    restaurant_id UUID,

    CONSTRAINT fk_table_restaurant_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    CONSTRAINT fk_table_restaurant_status FOREIGN KEY (status) REFERENCES table_status(code)
);
