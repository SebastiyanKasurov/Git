ALTER TABLE `git`.`files`
ADD COLUMN `is_removal` TINYINT(1) NOT NULL DEFAULT 0 AFTER `commit_id`;
