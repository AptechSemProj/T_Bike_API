
-- Create tables --

CREATE TABLE `brands` (
	`id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR ( 400 ) NOT NULL,
    `introduction` TEXT,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `categories` (
	`id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR ( 300 ) NOT NULL,
    `description` TEXT,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `orders` (
	`id` BIGINT AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `status` INT NOT NULL,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `order_details` (
    `order_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `total_amount` BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY ( `order_id`, `product_id` )
);

CREATE TABLE `products` (
--    identifier
	`id` BIGINT AUTO_INCREMENT,
    `sku` VARCHAR ( 20 ) NOT NULL,
--    basic information
    `brand_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `name` VARCHAR ( 300 ) NOT NULL,
    `size` VARCHAR ( 20 ) NOT NULL,
--    specifications
    `frame` TEXT NOT NULL,
    `saddle` TEXT NOT NULL,
    `seat_post` TEXT NOT NULL,
    `bell` TEXT,
    `fork` TEXT,
    `shock` TEXT,
--    Steering system
    `handlebar` TEXT NOT NULL,
    `handlebar_stem` TEXT NOT NULL,
--    power train system
    `pedal` TEXT NOT NULL,
    `crankset` TEXT NOT NULL,
    `bottom_bracket` TEXT NOT NULL,
    `chain` TEXT NOT NULL,
    `chain_guard` TEXT,
    `cassette` TEXT NOT NULL,
    `front_derailleur` TEXT,
    `rear_derailleur` TEXT,
--    Motion system
    `rims` TEXT NOT NULL,
    `hubs` TEXT NOT NULL,
    `spokes` TEXT NOT NULL,
    `tires` TEXT NOT NULL,
    `valve` TEXT NOT NULL,
--    Brake system
    `brakes` TEXT NOT NULL,
    `brake_levers` TEXT NOT NULL,

    PRIMARY KEY ( `id` ),
    UNIQUE ( `sku` )
);

CREATE TABLE `product_attributes` (
	`id` BIGINT AUTO_INCREMENT,
    `product_id` BIGINT NOT NULL,
    `represent` BIT ( 1 ) NOT NULL,
    `color` VARCHAR ( 50 ) NOT NULL,
    `image_url` TEXT NOT NULL,
    `price` BIGINT UNSIGNED DEFAULT 0,
    `quantity` INT DEFAULT 0,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `users` (
	`id` BIGINT AUTO_INCREMENT,
	`role` INT NOT NULL,
    `username` VARCHAR ( 100 ) NOT NULL,
    `password` CHAR ( 60 ) NOT NULL,
    `name` VARCHAR ( 100 ) NOT NULL,
    `phone_number` CHAR ( 10 ) NOT NULL,
    `avatar_image` TEXT,

    PRIMARY KEY ( `id` ),
    UNIQUE ( `username` )
);

-- Add constraints for tables --

ALTER TABLE `orders`
ADD CONSTRAINT `FK__orders_userId__users_id`
FOREIGN KEY ( `user_id` ) REFERENCES `users` ( `id` );

ALTER TABLE `order_details`
ADD CONSTRAINT `FK__orderDetails_orderId__orders_id`
FOREIGN KEY ( `order_id` ) REFERENCES `orders` ( `id` );

ALTER TABLE `order_details`
ADD CONSTRAINT `FK__orderDetails_productId__products_id`
FOREIGN KEY ( `product_id` ) REFERENCES `products` ( `id` );

ALTER TABLE `products`
ADD CONSTRAINT `FK__products_categoryId__categories_id`
FOREIGN KEY ( `category_id` ) REFERENCES `categories` ( `id` );

ALTER TABLE `products`
ADD CONSTRAINT `FK__products_brandId__brands_id`
FOREIGN KEY ( `brand_id` ) REFERENCES `brands` ( `id` );

ALTER TABLE `product_attributes`
ADD CONSTRAINT `FK__productAttributes_productId__products_id`
FOREIGN KEY ( `product_id` ) REFERENCES `products` ( `id` );

-- Create index --

CREATE INDEX `idx_account`
ON `users` ( `username`, `password` );

CREATE INDEX `FK__orderDetails_orderId__orders_id`
ON `order_details` ( `order_id` );
