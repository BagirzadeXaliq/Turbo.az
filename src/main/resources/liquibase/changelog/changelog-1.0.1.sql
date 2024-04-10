create table roles (
    role_id serial primary key,
    name varchar(255) not null
);

create table users (
    user_id bigserial primary key,
    username varchar(255) unique not null,
    password varchar(255) not null,
    status varchar(255)
);

create table cars (
    car_id bigserial primary key,
    brand varchar(255) not null,
    model varchar(255) not null,
    year integer,
    price double precision
);

create table images (
    image_id bigserial primary key,
    car_id bigint references cars(car_id),
    image_url varchar(255) not null
);

create table transactions (
    transaction_id bigserial primary key,
    buyer_user_id bigint references users(user_id),
    seller_user_id bigint references users(user_id),
    car_id bigint references cars(car_id),
    status varchar(255) not null
);

create table reviews (
    review_id bigserial primary key,
    user_id bigint references users(user_id),
    car_id bigint references cars(car_id),
    review varchar(255) not null
);

create table user_roles (
    user_id bigint not null references users(user_id),
    role_id integer not null references roles(role_id),
    primary key (user_id, role_id)
);

create table password_reset_tokens (
    id bigserial primary key,
    token varchar(255) not null,
    expiry_date timestamp not null,
    user_id bigint not null references users(user_id)
);