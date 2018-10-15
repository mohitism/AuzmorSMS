CREATE TABLE IF NOT EXISTS `phone_directory` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Auto generated value. Will be the primary key of the table.',
  `name` VARCHAR(128) NOT NULL COMMENT 'Name of the client',
  `phone_number` VARCHAR(128) NOT NULL COMMENT 'phone number of client',
  `status` VARCHAR(32) NOT NULL COMMENT 'Indicates the status of client \n\nEg :ACTIVE, INACTIVE.',
  `status_changed_on` DATETIME NOT NULL,
  `status_changed_remarks` VARCHAR(256) NOT NULL,
  `created_on` DATETIME NOT NULL,
  `created_by` VARCHAR(64) NOT NULL,
  `updated_on` DATETIME NOT NULL,
  `updated_by` VARCHAR(64) NOT NULL,
  `is_deleted` TINYINT(1) DEFAULT false,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_phone_number` (`phone_number` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'phone table stores the details of directory structure of the p' /* comment truncated */ /*latform owner.Each office definition will be as per the hierarchy template definition.*/;