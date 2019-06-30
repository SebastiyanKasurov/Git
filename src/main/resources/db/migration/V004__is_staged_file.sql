ALTER TABLE `files`
ADD COLUMN `is_staged` TINYINT(1) NOT NULL DEFAULT 0 AFTER `creation_date`;

ALTER TABLE `commits`
DROP COLUMN `is_staged`;
