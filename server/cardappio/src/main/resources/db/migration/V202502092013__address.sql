CREATE TABLE IF NOT EXISTS address (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    zip_code VARCHAR(8) NOT NULL,
    district VARCHAR(255) NOT NULL,
    number VARCHAR(10) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    city_id UUID,
    CONSTRAINT fk_address_city_id FOREIGN KEY (city_id) REFERENCES city(id)
);
