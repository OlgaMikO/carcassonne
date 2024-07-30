--liquibase formatted sql
--changeset mikhailova:tiles-0001

create table if not exists tiles
(
    id                      serial          primary key,
    top_element             text            not null,
    bottom_element          text            not null,
    left_element            text            not null,
    right_element           text            not null,
    center_element          text            not null
);