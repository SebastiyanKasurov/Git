CREATE TABLE `git`.`files` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `branch_id` BIGINT(20) NOT NULL,
  `repository_id` BIGINT(20) NOT NULL,
  `creation_date` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  INDEX `branch_fk_idx` (`branch_id` ASC) VISIBLE,
  INDEX `repo_fk_idx` (`repository_id` ASC) VISIBLE,
  CONSTRAINT `branch__fk`
    FOREIGN KEY (`branch_id`)
    REFERENCES `git`.`branches` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `repo_fk`
    FOREIGN KEY (`repository_id`)
    REFERENCES `git`.`repositories` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
