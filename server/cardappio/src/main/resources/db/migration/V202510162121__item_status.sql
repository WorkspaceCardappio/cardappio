CREATE TABLE IF NOT EXISTS item_status (
    code NUMERIC(10) PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO item_status (code, description) VALUES
(1, 'Único'),
(2, 'Pequeno'),
(3, 'Médio'),
(4, 'Grande');
