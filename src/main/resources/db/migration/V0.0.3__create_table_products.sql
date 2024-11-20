CREATE TABLE IF NOT EXISTS products
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    sku              VARCHAR(20)           NULL,
    name             VARCHAR(255)          NOT NULL,
    size             VARCHAR(20)           NOT NULL,
    frame            VARCHAR(200)          NOT NULL,
    saddle           VARCHAR(200)          NOT NULL,
    seat_post        VARCHAR(200)          NOT NULL,
    bell             VARCHAR(200)          NULL,
    fork             VARCHAR(200)          NULL,
    shock            VARCHAR(200)          NULL,
    handlebar        VARCHAR(200)          NOT NULL,
    handlebar_stem   VARCHAR(200)          NOT NULL,
    pedal            VARCHAR(200)          NOT NULL,
    crankset         VARCHAR(200)          NOT NULL,
    bottom_bracket   VARCHAR(200)          NOT NULL,
    chain            VARCHAR(200)          NOT NULL,
    chain_guard      VARCHAR(200)          NULL,
    cassette         VARCHAR(200)          NOT NULL,
    front_derailleur VARCHAR(200)          NULL,
    rear_derailleur  VARCHAR(200)          NULL,
    rims             VARCHAR(200)          NOT NULL,
    hubs             VARCHAR(200)          NOT NULL,
    spokes           VARCHAR(200)          NOT NULL,
    tires            VARCHAR(200)          NOT NULL,
    valve            VARCHAR(200)          NOT NULL,
    brakes           VARCHAR(200)          NOT NULL,
    brake_levers     VARCHAR(200)          NOT NULL,
    brand_id         BIGINT                NOT NULL,
    category_id      BIGINT                NOT NULL,
    CONSTRAINT pk_products
        PRIMARY KEY (id)
);

CREATE INDEX idx_product_name_brand_id
    ON products (name, brand_id, category_id, id);

CREATE INDEX idx_product_sku_id
    ON products (sku, id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_BRAND
        FOREIGN KEY (brand_id)
            REFERENCES brands (id);

CREATE INDEX idx_product_brand_id
    ON products (brand_id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY
        FOREIGN KEY (category_id)
            REFERENCES categories (id);

CREATE INDEX idx_product_category_id
    ON products (category_id);