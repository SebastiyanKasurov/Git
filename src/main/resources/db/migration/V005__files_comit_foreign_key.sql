ALTER TABLE `files`
ADD COLUMN `commit_id` BIGINT(20) NOT NULL AFTER `is_staged`,
ADD INDEX `commit_fk_idx` (`commit_id` ASC) VISIBLE;
ALTER TABLE `files`
ADD CONSTRAINT `commit_fk`
  FOREIGN KEY (`commit_id`)
  REFERENCES `commits` (`auto_increment`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
