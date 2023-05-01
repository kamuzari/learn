DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id            bigint        NOT NULL AUTO_INCREMENT,
    username      varchar(20)   NOT NULL,
    provider      varchar(20)   NOT NULL,
    provider_id   varchar(80)   NOT NULL,
    profile_image varchar(255)  DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unq_username UNIQUE (username),
    CONSTRAINT unq_provider_and_id UNIQUE (provider, provider_id)
);