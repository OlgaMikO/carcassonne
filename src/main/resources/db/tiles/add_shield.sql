--liquibase formatted sql
--changeset mikhailova:tiles-0002

alter table tiles
    add column shield boolean;