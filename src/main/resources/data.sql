-- insert into referee (id, name, surname, permission_class) values (1, 'szymon', 'dobrowolski', 'I');
-- insert into account (id, login, password, is_active, role) values (1, 'szm', '33', true, 'ADMIN');
-- insert into account (id, login, password, is_active, role) values (2, 'szm33', '3311', true, 'USER');
insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'REFEREE');
insert into license (id, type) values (1, 'CANDIDATE');
insert into license (id, type) values (2, 'THIRD_CLASS');
insert into license (id, type) values (3, 'SECOND_CLASS');
insert into license (id, type) values (4, 'FIRST_CLASS');
