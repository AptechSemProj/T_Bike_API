CREATE TABLE IF NOT EXISTS categories
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    deleted       BIT(1)                NOT NULL,
    name          VARCHAR(300)          NOT NULL,
    image_url     TEXT                  NOT NULL,
    `description` TEXT                  NULL,
    CONSTRAINT pk_categories
        PRIMARY KEY (id)
);

CREATE INDEX idx_category_deleted_id
    ON categories (deleted, id);

CREATE INDEX idx_category_name_id
    ON categories (name, id);