create table chat_messages
(
    id      bigint auto_increment
        primary key,
    user_id bigint,
    game_id bigint null,
    message_time datetime,
    messages varchar(255) null
);
