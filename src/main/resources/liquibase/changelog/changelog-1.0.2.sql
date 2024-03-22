insert into roles (name) values ('admin'), ('user');

insert into users (username, password) values ('khalig', 'khalig2009'), ('ali', 'ali123');

insert into user_roles (user_id, role_id)
values ((select user_id from users where username = 'khalig'), (select role_id from roles where name = 'admin')),
       ((select user_id from users where username = 'ali'), (select role_id from roles where name = 'user'));

-- insert into users (username, password)
-- values
--     ('khalig', '2009'),
--     ('eli', 'eli123'),
--     ('huseyin','hus2024');
--
-- insert into cars (brand, model, year, price)
-- values
--     ('Mercedes', 'E-Class', 2009, 25000.0),
--     ('BMW', 'M-series', 2012, 50000.0),
--     ('Ford', 'Mustang', 2015, 35000.0);
--
-- insert into images (car_id, image_url)
-- values
--     (1, 'https://turbo.azstatic.com/uploads/full/2024%2F01%2F09%2F11%2F05%2F58%2Ffbd8d341-bbfd-4258-9019-c447b8faff45%2F3803_DOAVQuEy1HljTH0-Evv-ow.jpg'),
--     (2, 'https://turbo.azstatic.com/uploads/full/2024%2F01%2F07%2F20%2F19%2F12%2Fa9f3b4cc-0949-47bb-9793-c8913cb5cdeb%2F3629_g0gBOLDITsPZTFAJ9Q6vlw.jpg'),
--     (3, 'https://turbo.azstatic.com/uploads/full/2023%2F10%2F01%2F15%2F57%2F26%2F5d7d6c32-2ee6-4bca-815d-6e6f1cd7ffd2%2F3628_nX7Gli5So5ZN7bCb0eCOHA.jpg');
--
-- insert into transactions (buyer_user_id, seller_user_id, car_id, status)
-- values
--     (1, 2, 1, 'In_Progress'),
--     (2, 3, 3, 'Completed'),
--     (3, 1, 2, 'Pending');
--
-- insert into reviews ( user_id, car_id, review)
-- values
--     (1, 2, 'Great Car'),
--     (2, 3, 'Nice Car'),
--     (3, 1, 'Bad Car');