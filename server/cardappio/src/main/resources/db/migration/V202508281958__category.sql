CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    image VARCHAR(255),
    category_id UUID,
    CONSTRAINT fk_category_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);
