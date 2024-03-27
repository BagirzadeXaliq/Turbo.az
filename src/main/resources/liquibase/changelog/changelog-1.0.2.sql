insert into roles (name) values ('admin'), ('user');

insert into users (username, password) values ('khalig', 'khalig2009'), ('ali', 'ali123');

insert into user_roles (user_id, role_id)
values ((select user_id from users where username = 'khalig'), (select role_id from roles where name = 'admin')),
       ((select user_id from users where username = 'ali'), (select role_id from roles where name = 'user'));