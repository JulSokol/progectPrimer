create table if not exists role
(
    id bigint auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists user
(
    id bigint auto_increment
        primary key,
    password varchar(255) null,
    username varchar(255) null
);

create table if not exists user_roles
(
    users_id bigint not null,
    roles_id bigint not null,
    primary key (users_id, roles_id),
    constraint fk_user_roles_user_id
        foreign key (users_id) references user (id),
    constraint fk_user_roles_role_id
        foreign key (roles_id) references role (id)
);
