CREATE TABLE IF NOT EXISTS person (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(2) NOT NULL,
    document VARCHAR(14) UNIQUE,
    phone VARCHAR(11),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    active BOOLEAN NOT NULL,
    address_id UUID NOT NULL,

    CONSTRAINT fk_person_type_id FOREIGN KEY (type) REFERENCES person_type(type),
    CONSTRAINT fk_person_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);

insert into person(id, name, type, document, password, email, active, address_id) values ('0ad8e87d-a9db-4746-823d-eeb7cd0efb10', 'Darth Vader', 'PF', '51919807004', '1234', 'dart@gmail.com', true, '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');