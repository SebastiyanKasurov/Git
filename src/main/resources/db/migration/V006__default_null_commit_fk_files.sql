ALTER TABLE `files`
DROP FOREIGN KEY `commit_fk`;
ALTER TABLE `files`
CHANGE COLUMN `commit_id` `commit_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `files`
ADD CONSTRAINT `commit_fk`
  FOREIGN KEY (`commit_id`)
  REFERENCES `commits` (`auto_increment`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;