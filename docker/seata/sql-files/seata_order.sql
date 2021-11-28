CREATE TABLE `order_tbl` (
     `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
     `product_id` int(11) DEFAULT NULL,
     `total_amount` int(11) DEFAULT NULL,
     `status` int(11) DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;