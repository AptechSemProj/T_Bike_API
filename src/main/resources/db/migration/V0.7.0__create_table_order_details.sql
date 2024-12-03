CREATE TABLE IF NOT EXISTS order_details
(
    price        BIGINT NOT NULL,
    quantity     INT    NOT NULL,
    total_amount BIGINT NOT NULL,
    order_id     BIGINT NOT NULL,
    product_id   BIGINT NOT NULL,
    CONSTRAINT pk_order_details
        PRIMARY KEY (order_id, product_id)
);

CREATE INDEX idx_order_detail_order_id
    ON order_details (order_id);

CREATE INDEX idx_order_detail_quantity
    ON order_details (order_id, quantity, total_amount);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_ORDER
        FOREIGN KEY (order_id)
            REFERENCES orders (id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_PRODUCT
        FOREIGN KEY (product_id)
            REFERENCES attributes (id);