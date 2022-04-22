-- SEQUENCE
create sequence sq_simple_auth_authority start with 1 increment by 1 no cycle;
create sequence sq_simple_auth_user start with 1 increment by 1 no cycle;

-- TABLE
create table simple_auth_authority
(
    id   bigint       not null,
    name varchar(255) not null
);

create table simple_auth_user
(
    id       bigint       not null,
    username varchar(255) not null,
    password varchar(255) not null,
    enabled  bool         not null
);

create table simple_auth_user_authority
(
    user_id      bigint not null,
    authority_id bigint not null
);

create table simple_auth_user_attribute
(
    user_id bigint       not null,
    key     varchar(255) not null,
    value   varchar(255) not null
);

-- PK
alter table simple_auth_authority
    add primary key (id);

alter table simple_auth_user
    add primary key (id);

-- UNIQUE
alter table simple_auth_authority
    add constraint u_simple_auth_authority unique (name);

alter table simple_auth_user
    add constraint u_simple_auth_user unique (username);

alter table simple_auth_user_authority
    add constraint u_simple_auth_user_authority unique (user_id, authority_id);

alter table simple_auth_user_attribute
    add constraint u_simple_auth_user_attribute unique (user_id, key);

-- FK
alter table simple_auth_user_authority
    add foreign key (authority_id) references simple_auth_authority (id) on delete cascade;
alter table simple_auth_user_authority
    add foreign key (user_id) references simple_auth_user (id) on delete cascade;

alter table simple_auth_user_attribute
    add foreign key (user_id) references simple_auth_user (id) on delete cascade;

-- INDEX
create index idx_simple_auth_user on simple_auth_user (username);

create index idx_simple_auth_user_authority_1 on simple_auth_user_authority (authority_id);
create index idx_simple_auth_user_authority_2 on simple_auth_user_authority (user_id);

create index idx_simple_auth_user_attribute on simple_auth_user_attribute (user_id);

-- DATA
insert into simple_auth_authority(id, name)
values (nextval('sq_simple_auth_authority'), 'view-users');
insert into simple_auth_authority(id, name)
values (nextval('sq_simple_auth_authority'), 'manage-users');
