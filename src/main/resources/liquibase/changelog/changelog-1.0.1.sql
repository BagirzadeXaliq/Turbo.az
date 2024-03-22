create table roles (
    role_id serial primary key,
    name varchar(255) not null
);

create table users (
    user_id serial primary key,
    username varchar(255) unique not null,
    password varchar(255) not null
);

create table cars (
    car_id serial primary key,
    brand varchar(255) not null,
    model varchar(255) not null,
    year integer,
    price double precision
);

create table images (
    image_id serial primary key,
    car_id bigint references cars(car_id),
    image_url varchar(255) not null
);

create table transactions (
    transaction_id serial primary key,
    buyer_user_id bigint references users(user_id),
    seller_user_id bigint references users(user_id),
    car_id bigint references cars(car_id),
    status varchar(255) not null
);

create table reviews (
    review_id serial primary key,
    user_id bigint references users(user_id),
    car_id bigint references cars(car_id),
    review text not null
);

create table user_roles (
    user_id bigint references users(user_id) ,
    role_id integer references roles(role_id),
    primary key (user_id, role_id)
);