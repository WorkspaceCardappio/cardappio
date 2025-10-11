INSERT INTO ingredient(id, name, quantity, expiration_date, allergenic) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', 'Sal', '10', '2025-11-10', false);

INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', 'Batata', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');
INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('0d8b7a7b-558c-44e4-ad9e-d733961b387d', 'Feijão', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');
INSERT INTO product(id, name, price, quantity, expiration_date, category_id) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 'Carne', '12.50', '1', '2025-11-10', '0ad8e87d-a9db-4746-823d-eeb7cd0efb10');

INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');
INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('0d8b7a7b-558c-44e4-ad9e-d733961b387d', '0d8b7a7b-558c-44e4-ad9e-d733961b387d', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');
INSERT INTO product_ingredient(id, product_id, ingredient_id, quantity) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 'defba662-54a0-4b98-b2e6-8e4421aed15c', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', '1');

INSERT INTO ticket(id, number, total, status, person_id, table_id) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 1, 50, 1, '0ad8e87d-a9db-4746-823d-eeb7cd0efb10', 'df70242c-93ef-4748-a306-b8f02912ba5e');

insert into client_order(id, total, status, ticket_id) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 26, 1, 'defba662-54a0-4b98-b2e6-8e4421aed15c');
insert into product_order(id, client_order_id, product_id, quantity, price, total) values ('defba662-54a0-4b98-b2e6-8e4421aed15c', 'defba662-54a0-4b98-b2e6-8e4421aed15c', '6bbb8d61-61e4-4f45-a13c-8b2a1f807894', 2, 13, 25);

insert into client_order(id, total, status, ticket_id) values ('c69a173c-2156-4f49-b9d9-093b551e3099', 12.50, 1, 'defba662-54a0-4b98-b2e6-8e4421aed15c');
insert into product_order(id, client_order_id, product_id, quantity, price, total) values ('4319b5ad-6b06-419d-b755-487dff1188c9', 'c69a173c-2156-4f49-b9d9-093b551e3099', '0d8b7a7b-558c-44e4-ad9e-d733961b387d', 1, 12.50, 12.50);

insert into client_order(id, total, status, ticket_id) values ('80701206-d175-46af-aac1-f7afc5d82189', 12.50, 1, 'defba662-54a0-4b98-b2e6-8e4421aed15c');
insert into product_order(id, client_order_id, product_id, quantity, price, total) values ('36056118-41d0-4148-97ab-1cf3f46b0850', '80701206-d175-46af-aac1-f7afc5d82189', 'defba662-54a0-4b98-b2e6-8e4421aed15c', 1, 12.50, 12.50);