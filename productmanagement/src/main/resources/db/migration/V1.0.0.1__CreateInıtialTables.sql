CREATE TABLE category (
    id serial,
    created_at DATE NOT NULL,
    updated_at DATE,
    deleted_at DATE,
    header varchar(50) NOT NULL,
    category varchar(50) NOT NULL,
    sub_category varchar(50) NOT NULL,
    primary key(id)

);

CREATE TABLE products (
    id serial,
    created_at DATE NOT NULL,
    updated_at DATE,
    deleted_at DATE,
    price bigint NOT NULL,
    brand varchar(50) NOT NULL,
    description varchar(150) NOT NULL,
    category_id bigint NOT NULL,
    constraint fk_product_category foreign key(category_id) references category(id),

    primary key(id)

);


CREATE TABLE inventory (
    id serial,
    created_at DATE NOT NULL,
    updated_at DATE,
    deleted_at DATE,
    product_quantity bigint NOT NULL,
    product_id bigint NOT NULL,
    primary key(id)

);