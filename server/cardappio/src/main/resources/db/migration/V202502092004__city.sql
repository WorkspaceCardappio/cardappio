CREATE TABLE IF NOT EXISTS city (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    state_id NUMERIC(10),
    CONSTRAINT fk_city_state_id FOREIGN KEY (state_id) REFERENCES state(id)
);