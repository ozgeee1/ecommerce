CREATE TABLE users (
    id serial,
    created_at DATE,
    updated_at DATE,
    deleted_at DATE,
    first_name varchar(50) ,
    last_name varchar(50) ,
    email varchar(50) NOT NULL,
    phone_number varchar(11),
    birth_date DATE ,
    selected_address_Id bigint,
    is_phone_number_verified BOOLEAN,

    primary key(id)

);

CREATE TABLE address (
    id serial,
    created_at DATE ,
    updated_at DATE,
    deleted_at DATE,
    header varchar(100) NOT NULL,
    city varchar(50) NOT NULL,
    district varchar(50) NOT NULL,
    address_line varchar(50),
    zipcode varchar(10),
    user_id bigint NOT NULL,

    constraint fk_user_address foreign key(user_id) references users(id),
    primary key(id)

);