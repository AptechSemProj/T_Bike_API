create table `banner`
(
    `id`         bigint primary key auto_increment,
    `image_url`  TEXT,
    `product_id` bigint
);

alter table `order_details`
    add `price` bigint not null;