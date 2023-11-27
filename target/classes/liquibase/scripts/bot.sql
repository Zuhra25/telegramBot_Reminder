-- liquibase formatted sql

-- changeset zshaihova:1
CREATE TABLE notification_task
(
    id        bigserial primary key,
    chat_id   bigserial not null,
    task      varchar,
    task_time timestamp
);