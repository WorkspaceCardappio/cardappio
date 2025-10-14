CREATE TABLE IF NOT EXISTS client_order(
    id UUID PRIMARY KEY,
    total DECIMAL(10,2) NOT NULL,
    status NUMERIC(10),
    ticket_id UUID NOT NULL,

    CONSTRAINT fk_client_order_status_id FOREIGN KEY (status) REFERENCES order_status(code),
    CONSTRAINT fk_client_order_ticket_id FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);