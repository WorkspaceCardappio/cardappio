CREATE TABLE IF NOT EXISTS ingredient (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    quantity DECIMAL(10,2) NOT NULL CHECK (quantity >= 0),
    expiration_date DATE NOT NULL,
    unit_of_measurement NUMERIC(10) NOT NULL,
    allergenic BOOLEAN NOT NULL DEFAULT FALSE
);