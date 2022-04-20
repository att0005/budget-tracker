CREATE DATABASE IF NOT EXISTS budgetingapp;

use budgetingapp;

CREATE TABLE IF NOT EXISTS `user` (
 `id` INT auto_increment,
  `email` varchar(255) NOT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `category` (
`id` INT auto_increment,
  `user_id` INT,
  `title` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`)
);

CREATE TABLE IF NOT EXISTS `account` (
  `id` INT auto_increment,
  `name` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `user_id` INT,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`)
) ;

CREATE TABLE IF NOT EXISTS `transaction` (
  `id` INT auto_increment,
  `title` varchar(100) NOT NULL,
  `amount` INT NOT NULL,
  `category_id` INT,
  `user_id` INT,
  `account_id` INT,
  `type` varchar(100) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  FOREIGN KEY (`account_id`) REFERENCES account(`id`),
  FOREIGN KEY (`category_id`) REFERENCES category(`id`)
) ;


CREATE TABLE IF NOT EXISTS `budget` (
  `id` INT auto_increment,
  `estimated_expense` INT DEFAULT 0,
  `estimated_income` INT DEFAULT 0,
  `category_id` INT,
  `user_id` INT,
  `month` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  FOREIGN KEY (`category_id`) REFERENCES category(`id`)
) ;

