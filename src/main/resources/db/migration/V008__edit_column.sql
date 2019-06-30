ALTER TABLE `branches`
CHANGE COLUMN `repository_id` `repository_id` BIGINT(20) NULL ;

ALTER TABLE `files`
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT ;
