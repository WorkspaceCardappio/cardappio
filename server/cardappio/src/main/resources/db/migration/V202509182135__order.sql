CREATE SEQUENCE IF NOT EXISTS client_order_number_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS client_order(
    id UUID PRIMARY KEY,
    number NUMERIC(18) NOT NULL DEFAULT nextval('client_order_number_seq') UNIQUE,
    total DECIMAL(10,2) NOT NULL,
    status NUMERIC(10),
    ticket_id UUID NOT NULL,
    save_status VARCHAR(10) NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    external_reference_id VARCHAR(255) UNIQUE,

    CONSTRAINT fk_client_order_status_id FOREIGN KEY (status) REFERENCES order_status(code),
    CONSTRAINT fk_client_order_ticket_id FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);