CREATE TABLE users (
    id serial,
    created_at DATE NOT NULL,
    updated_at DATE,
    deleted_at DATE,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(50) NOT NULL,
    phone_number varchar(11) NOT NULL,
    birth_date DATE NOT NULL,
    selected_address_Id bigint,

    primary key(id)

);

CREATE TABLE address (
    id serial,
    created_at DATE NOT NULL,
    updated_at DATE,
    deleted_at DATE,
    header varchar(100) NOT NULL,
    city varchar(50) NOT NULL,
    street varchar(50) NOT NULL,
    address_line1 varchar(50),
    address_line2 varchar(50),
    zipcode varchar(10),
    user_id bigint NOT NULL,

    constraint fk_user_address foreign key(user_id) references users(id),
    primary key(id)

);