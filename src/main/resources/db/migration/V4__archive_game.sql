create table archive_game
(
    id      bigint auto_increment
        primary key,
    game_id bigint null,
    white_id bigint,
    black_id bigint,
    vin_user_id bigint null,
    game_time datetime
);

alter table archive_game add constraint FKp0d95a3u7ugnv56dljfld66g foreign key (black_id) references user (id);
alter table archive_game add constraint FKgsx66pp33fky9e0yi5ih5yhvd foreign key (vin_user_id) references user (id);
alter table archive_game add constraint FKt0vv9kqjjgirsoh2rbb6d42ye foreign key (white_id) references user (id);
