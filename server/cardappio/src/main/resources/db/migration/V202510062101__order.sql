CREATE TABLE IF NOT EXISTS client_order (
    id UUID PRIMARY KEY,
    number BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    ticket_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_order_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id)
    );