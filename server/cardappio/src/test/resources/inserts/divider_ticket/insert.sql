INSERT INTO ingredient(id, name, quantity, expiration_date, allergenic) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', 'Sal', '10', '2025-11-10', false);

INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', 'Batata', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');
INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('0d8b7a7b-558c-44e4-ad9e-d733961b387d', 'Feijão', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');
INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 'Carne', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');

INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');
INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('0d8b7a7b-558c-44e4-ad9e-d733961b387d', '0d8b7a7b-558c-44e4-ad9e-d733961b387d', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');
INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 'defba662-54a0-4b98-b2e6-8e4421aed15c', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');