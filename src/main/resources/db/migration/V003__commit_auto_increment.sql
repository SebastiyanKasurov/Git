ALTER TABLE `git`.`commits`
ADD COLUMN `auto_increment` BIGINT(20) NOT NULL AUTO_INCREMENT AFTER `creation_date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`auto_increment`);