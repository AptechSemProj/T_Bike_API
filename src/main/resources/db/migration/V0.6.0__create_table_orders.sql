CREATE TABLE IF NOT EXISTS orders
(
    id           BIGINT AUTO_INCREMENT   NOT NULL,
    total_amount BIGINT                  NOT NULL,
    created_at   timestamp DEFAULT NOW() NULL,
    status       VARCHAR(255)            NOT NULL,
    user_id      BIGINT                  NOT NULL,
    CONSTRAINT pk_orders
        PRIMARY KEY (id)
);

CREATE INDEX idx_order_created_at_id
    ON orders (created_at, id);

CREATE INDEX idx_order_user_id_id
    ON orders (user_id, id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER
        FOREIGN KEY (user_id)
            REFERENCES users (id);