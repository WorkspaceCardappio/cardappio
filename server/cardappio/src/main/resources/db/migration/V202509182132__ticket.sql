CREATE SEQUENCE IF NOT EXISTS ticket_number_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS ticket (
    id UUID PRIMARY KEY,
    number NUMERIC(18) NOT NULL DEFAULT nextval('ticket_number_seq') UNIQUE,
    status NUMERIC(10),
    person_id UUID,
    table_id UUID,

    CONSTRAINT fk_ticket_person_id FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT fk_ticket_table_id FOREIGN KEY (table_id) REFERENCES table_restaurant(id),
    CONSTRAINT fk_ticket_status_id FOREIGN KEY (status) REFERENCES ticket_status(code)
);