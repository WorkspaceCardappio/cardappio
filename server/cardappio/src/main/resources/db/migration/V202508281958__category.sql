CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    image VARCHAR(255),
    category_id UUID,
    archive_id NUMERIC(10),
    CONSTRAINT fk_category_category_id FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT fk_category_archive_id FOREIGN KEY (archive_id) REFERENCES archive(id)
);
