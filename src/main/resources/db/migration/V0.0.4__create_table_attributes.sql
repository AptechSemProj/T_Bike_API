CREATE TABLE IF NOT EXISTS attributes
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    represent  BIT(1)                NOT NULL,
    color      VARCHAR(50)           NOT NULL,
    image_url  TEXT                  NOT NULL,
    price      BIGINT DEFAULT 0      NULL,
    quantity   INT    DEFAULT 0      NULL,
    product_id BIGINT                NOT NULL,
    CONSTRAINT pk_attributes
        PRIMARY KEY (id)
);

ALTER TABLE attributes
    ADD CONSTRAINT FK_ATTRIBUTES_ON_PRODUCT
        FOREIGN KEY (product_id)
            REFERENCES products (id);

CREATE INDEX idx_attribute_price_id
    ON attributes (price, id);

CREATE INDEX idx_attribute_product_id_id
    ON attributes (product_id, id, represent);

CREATE INDEX idx_attribute_represent_id
    ON attributes (represent, id);
