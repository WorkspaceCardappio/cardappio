CREATE TABLE IF NOT EXISTS person (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type_id INTEGER NOT NULL,
    document VARCHAR(14) UNIQUE,
    phone VARCHAR(11),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    active BOOLEAN NOT NULL,
    address_id UUID NOT NULL,

    CONSTRAINT fk_person_type_id FOREIGN KEY (type_id) REFERENCES person_type(id),
    CONSTRAINT fk_person_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);