CREATE TABLE IF NOT EXISTS archive (
    id NUMERIC(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    mime_type VARCHAR(200) NOT NULL,
    full_path VARCHAR(1200) NOT NULL,
    created_at DATE NOT NULL,
    archive_length NUMERIC(10) NOT NULL
);
