CREATE TABLE IF NOT EXISTS order_status (
    code NUMERIC(10) PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO order_status (code, description) VALUES
(1, 'Pendente'),
(2, 'Em Andamento'),
(3, 'Finalizado'),
(4, 'Entregue');