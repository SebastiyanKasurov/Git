CREATE TABLE `repositories` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `current_branch_id` BIGINT(20),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

CREATE TABLE `branches` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `repository_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

CREATE TABLE `commits` (
  `id` VARCHAR(50) NOT NULL,
  `message` VARCHAR(45) NOT NULL,
  `branch_id` BIGINT(20) NOT NULL,
  `is_staged` TINYINT(1) NOT NULL DEFAULT 0,
  `creation_date` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
DEFAULT CHARSET = utf8;

ALTER TABLE `repositories`
ADD INDEX `current_branch_fk_idx` (`current_branch_id` ASC) VISIBLE;

ALTER TABLE `repositories`
ADD CONSTRAINT `current_branch_fk`
  FOREIGN KEY (`current_branch_id`)
  REFERENCES `branches` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE .`commits`
ADD INDEX `branch_fk_idx` (`branch_id` ASC) VISIBLE;
ALTER TABLE `commits`
ADD CONSTRAINT `branch_fk`
  FOREIGN KEY (`branch_id`)
  REFERENCES `branches` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

