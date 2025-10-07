CREATE TABLE IF NOT EXISTS restaurant (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    address_id UUID,
    cnpj VARCHAR(14) NOT NULL,
    CONSTRAINT fk_restaurant_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);
