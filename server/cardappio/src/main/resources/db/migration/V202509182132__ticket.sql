CREATE TABLE IF NOT EXISTS ticket (
    id UUID PRIMARY KEY,
    number VARCHAR(10) NOT NULL UNIQUE,
    status NUMERIC(10),
    person_id UUID,
    table_id UUID,
    CONSTRAINT fk_ticket_person_id FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT fk_ticket_table_id FOREIGN KEY (table_id) REFERENCES table_restaurant(id),
    CONSTRAINT fk_ticket_status_id FOREIGN KEY (status) REFERENCES ticket_status(code)
);