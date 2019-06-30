ALTER TABLE `commits`
ADD COLUMN `is_head` TINYINT(1) NOT NULL DEFAULT 0 AFTER `auto_increment`;
