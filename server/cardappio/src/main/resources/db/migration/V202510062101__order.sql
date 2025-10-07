CREATE TABLE IF NOT EXISTS client_order (
    id UUID PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    ticket_id UUID NOT NULL,
    CONSTRAINT fk_order_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id)
    );