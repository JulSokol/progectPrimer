create table if not exists role
(
    id bigint auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists user
(
    id bigint auto_increment primary key,
    password varchar(255) null,
    username varchar(255) null,
    email varchar(255) null,
    city varchar(255) null,
    country varchar(255) null,
    current_game_id bigint null,
    nik varchar(255) null
);

create table if not exists user_roles
(
    users_id bigint auto_increment,
    roles_id bigint not null,
    primary key (users_id, roles_id),
    constraint fk_user_roles_user_id
        foreign key (users_id) references user (id),
    constraint fk_user_roles_role_id
        foreign key (roles_id) references role (id)
);


INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO user (password, username) VALUES ('$2a$10$a2WYZqGW/EJ2R02KJGmh8OaAUm/f3lNyCfcK19BDuG8hYsW6O6WsO', 'admin123');
INSERT INTO user_roles (users_id, roles_id) VALUES (1, 1);