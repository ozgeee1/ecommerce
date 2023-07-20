CREATE TABLE authorities (
     id serial,
     created_at DATE NOT NULL,
     updated_at DATE,
     deleted_at DATE,
     role varchar(50) NOT NULL,
     user_id int NOT NULL,
     constraint fk_user_authorities FOREIGN KEY (user_id) REFERENCES users(id),

     primary key(id)

);

INSERT INTO authorities (created_at,role,user_id)
VALUES ('2023-07-09 11:55:47','USER',1);

INSERT INTO authorities (created_at,role,user_id)
VALUES ('2023-07-09 11:55:47','USER',2);

INSERT INTO authorities (created_at,role,user_id)
VALUES ('2023-07-09 11:55:47','USER',3);

INSERT INTO authorities (created_at,role,user_id)
VALUES ('2023-07-09 11:55:47','USER',4);

INSERT INTO authorities (created_at,role,user_id)
VALUES ('2023-07-09 11:55:47','USER',5);




