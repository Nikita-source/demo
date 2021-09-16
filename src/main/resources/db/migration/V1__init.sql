CREATE TABLE addresses
(
    id      bigint       NOT NULL PRIMARY KEY,
    address varchar(255) NOT NULL
);

CREATE TABLE categories
(
    id    bigint       NOT NULL PRIMARY KEY,
    title varchar(255) NOT NULL
);

CREATE TABLE customers
(
    id          bigint       NOT NULL PRIMARY KEY,
    email       varchar(255) NOT NULL,
    lastname    varchar(255) NOT NULL,
    name        varchar(255) NOT NULL,
    phonenumber varchar(255) NOT NULL,
    secondname  varchar(255) NOT NULL
);

CREATE TABLE orders
(
    id          bigint NOT NULL PRIMARY KEY,
    customer_id bigint,
    product_id  bigint
);

CREATE TABLE product_categories
(
    product_id  bigint NOT NULL,
    category_id bigint NOT NULL,
    PRIMARY KEY (product_id, category_id)
);

CREATE TABLE products
(
    id           bigint         NOT NULL PRIMARY KEY,
    availability boolean,
    description  varchar(255)   NOT NULL,
    price        numeric(19, 2) NOT NULL,
    title        varchar(255)   NOT NULL
);

CREATE TABLE roles
(
    id   bigint NOT NULL PRIMARY KEY,
    name varchar(20)
);

CREATE TABLE shopinventories
(
    id         bigint NOT NULL PRIMARY KEY,
    count      bigint,
    product_id bigint,
    shop_id    bigint
);

CREATE TABLE shops
(
    id         bigint       NOT NULL PRIMARY KEY,
    title      varchar(255) NOT NULL,
    address_id bigint
);

CREATE TABLE supplies
(
    id           bigint NOT NULL PRIMARY KEY,
    count        bigint,
    date         timestamp without time zone,
    product_id   bigint,
    shop_id      bigint,
    warehouse_id bigint
);

CREATE TABLE user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE users
(
    id          bigint                      NOT NULL PRIMARY KEY,
    birthday    timestamp without time zone NOT NULL,
    email       varchar(255)                NOT NULL,
    login       varchar(255)                NOT NULL,
    name        varchar(255)                NOT NULL,
    password    varchar(255)                NOT NULL,
    phonenumber varchar(255)                NOT NULL,
    surname     varchar(255)                NOT NULL
);

CREATE TABLE warehouseinventories
(
    id           bigint NOT NULL PRIMARY KEY,
    count        bigint,
    product_id   bigint,
    warehouse_id bigint
);

CREATE TABLE warehouses
(
    id         bigint NOT NULL PRIMARY KEY,
    address_id bigint
);

create unique index addresses_address_uindex on addresses (address);
create unique index shops_title_uindex on shops (title);
create unique index users_email_uindex on users (email);
create unique index users_phonenumber_uindex on users (phonenumber);
create unique index users_login_uindex on users (login);
create unique index products_title_uindex on products (title);
create unique index customers_email_uindex on customers (email);
create unique index customers_phonenumber_uindex on customers (phonenumber);
create unique index categories_title_uindex on categories (title);

ALTER TABLE ONLY warehouseinventories
    ADD CONSTRAINT warehouseinventories_products_fk FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE ONLY warehouseinventories
    ADD CONSTRAINT warehouseinventories_warehouses_fk FOREIGN KEY (warehouse_id) REFERENCES warehouses (id);
ALTER TABLE ONLY warehouses
    ADD CONSTRAINT warehouses_addresses_fk FOREIGN KEY (address_id) REFERENCES addresses (id);
ALTER TABLE ONLY supplies
    ADD CONSTRAINT supplies_products_fk FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE ONLY supplies
    ADD CONSTRAINT supplies_shops_fk FOREIGN KEY (shop_id) REFERENCES shops (id);
ALTER TABLE ONLY supplies
    ADD CONSTRAINT supplies_warehouses_fk FOREIGN KEY (warehouse_id) REFERENCES warehouses (id);
ALTER TABLE ONLY shops
    ADD CONSTRAINT shops_addresses_fk FOREIGN KEY (address_id) REFERENCES addresses (id);
ALTER TABLE ONLY shopinventories
    ADD CONSTRAINT shopinventories_shops_fk FOREIGN KEY (shop_id) REFERENCES shops (id);
ALTER TABLE ONLY shopinventories
    ADD CONSTRAINT shopinventories_products_fk FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE ONLY product_categories
    ADD CONSTRAINT product_categories_categories_fk FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;
ALTER TABLE ONLY product_categories
    ADD CONSTRAINT product_categories_products_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;
ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_roles_fk FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE;
ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_users_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_products_fk FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_customers_fk FOREIGN KEY (customer_id) REFERENCES customers (id);

INSERT INTO roles(id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO roles(id, name)
VALUES (2, 'ROLE_USER');

INSERT INTO users(id, login, password, email, phonenumber, name, surname, birthday)
VALUES (1, 'admin', '$2a$10$uUez5uAWnlfn8ytN1cHLnub/P2xZq//iST59RxBwXe7TWl349gAz.', 'admin@mail', 'admin-phone',
        'admin', 'admin', '1990-01-01 00:00:00.947000');

INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
       (1, 2);