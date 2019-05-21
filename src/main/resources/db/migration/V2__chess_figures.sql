create table chess_figures
(
  id      bigint auto_increment
    primary key,
  figures varchar(255) null,
  move_color varchar(255),
  black_id bigint,
  white_id bigint
);

alter table chess_figures add constraint FK93bu3d35hybuc22ox74kmahhg foreign key (black_id) references user (id);
alter table chess_figures add constraint FKkt4tbfpt25c8ryfrqi0mbexcu foreign key (white_id) references user (id);
