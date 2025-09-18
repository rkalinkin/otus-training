create sequence address_SEQ start with 1 increment by 1;
create table address (
    id bigint not null primary key,
    street varchar(255),
    client_id bigint references client(id) on delete cascade
);

create sequence phone_SEQ start with 1 increment by 1;
create table phone (
    id bigint not null primary key,
    number varchar(20),
    client_id bigint references client(id) on delete cascade
);
