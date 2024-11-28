CREATE TABLE IF NOT EXISTS brands
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    deleted       BIT(1)                NOT NULL,
    name          VARCHAR(400)          NOT NULL,
    image_url     TEXT                  NOT NULL,
    `description` TEXT                  NULL,
    CONSTRAINT pk_brands
        PRIMARY KEY (id)
);

CREATE INDEX idx_brand_name_id
    ON brands (name, id);

CREATE INDEX idx_brand_deleted_id
    ON brands (deleted, id);