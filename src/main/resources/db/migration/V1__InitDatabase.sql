
-- Create tables --

CREATE TABLE `brands` (
	`id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR ( 400 ) NOT NULL,
    `description` TEXT,
    `image_url` TEXT NOT NULL,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `categories` (
	`id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR ( 300 ) NOT NULL,
    `description` TEXT,
    `image_url` TEXT NOT NULL,

    PRIMARY KEY ( `id` )
);

CREATE TABLE `orders` (
	`id` BIGINT AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `total_amount` BIGINT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM(
    'cart', 'purchased', 'refunded', 'waiting', 'shipping', 'shipped', 'done'
    ) DEFAULT 'cart',

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
    `sku` VARCHAR ( 20 ),
--    basic information
    `brand_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `name` VARCHAR ( 255 ) NOT NULL,
    `size` VARCHAR ( 20 ) NOT NULL,
--    specifications
    `frame` VARCHAR ( 200 ) NOT NULL,
    `saddle` VARCHAR ( 200 ) NOT NULL,
    `seat_post` VARCHAR ( 200 ) NOT NULL,
    `bell` VARCHAR ( 200 ),
    `fork` VARCHAR ( 200 ),
    `shock` VARCHAR ( 200 ),
--    Steering system
    `handlebar` VARCHAR ( 200 ) NOT NULL,
    `handlebar_stem` VARCHAR ( 200 ) NOT NULL,
--    power train system
    `pedal` VARCHAR ( 200 ) NOT NULL,
    `crankset` VARCHAR ( 200 ) NOT NULL,
    `bottom_bracket` VARCHAR ( 200 ) NOT NULL,
    `chain` VARCHAR ( 200 ) NOT NULL,
    `chain_guard` VARCHAR ( 200 ),
    `cassette` VARCHAR ( 200 ) NOT NULL,
    `front_derailleur` VARCHAR ( 200 ),
    `rear_derailleur` VARCHAR ( 200 ),
--    Motion system
    `rims` VARCHAR ( 200 ) NOT NULL,
    `hubs` VARCHAR ( 200 ) NOT NULL,
    `spokes` VARCHAR ( 200 ) NOT NULL,
    `tires` VARCHAR ( 200 ) NOT NULL,
    `valve` VARCHAR ( 200 ) NOT NULL,
--    Brake system
    `brakes` VARCHAR ( 200 ) NOT NULL,
    `brake_levers` VARCHAR ( 200 ) NOT NULL,

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
    `status` BIT( 1 ) NOT NULL,

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
