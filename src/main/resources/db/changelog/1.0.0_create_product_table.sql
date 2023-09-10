--liquibase formatted sql

-- changeset liquibase-docs:createProductTable
create table if not exists products
(
    id    bigserial primary key not null,
    name  varchar(255)          not null,
    sku   varchar(255)          not null,
    price real default 0
);