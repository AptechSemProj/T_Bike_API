CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    deleted      BIT(1)                NOT NULL,
    `role`       VARCHAR(255)          NOT NULL,
    username     VARCHAR(100)          NOT NULL,
    password     CHAR(60)              NOT NULL,
    name         VARCHAR(100)          NOT NULL,
    phone_number CHAR(10)              NOT NULL,
    avatar_image TEXT                  NULL,
    CONSTRAINT pk_users
        PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username
        UNIQUE (username);

CREATE INDEX idx_user_account
    ON users (username, password, deleted, id);

CREATE INDEX idx_user_id_deleted
    ON users (deleted, id);

CREATE INDEX idx_user_username_id_deleted
    ON users (username, id, deleted);