create table chat_messages
(
    id      bigint auto_increment
        primary key,
    user_id bigint,
    game_id bigint null,
    message_time datetime,
    message varchar(255) null
);

alter table chat_messages add constraint FKbsqde5d10bcmxatteetweokl foreign key (user_id) references user (id);
