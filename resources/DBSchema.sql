-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `enjoytrip`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `enjoytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `tel` VARCHAR(30) NOT NULL,
  `profileimg` BLOB NULL DEFAULT NULL,
  `email` VARCHAR(50) NOT NULL,
  `admin` TINYINT NULL DEFAULT '0',
  `last_access` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `review_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `register_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `comment_to_review_review_id_FK_idx` (`review_id` ASC) VISIBLE,
  INDEX `comment_to_user_user_id_FK_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `comment_to_user_user_id_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan` (
  `plan_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(30) NOT NULL,
  `start_date` DATE NULL DEFAULT NULL,
  `end_date` DATE NULL DEFAULT NULL,
  `last_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`plan_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`course` (
  `content_id` INT NULL DEFAULT NULL,
  `plan_id` INT NULL DEFAULT NULL,
  `order` INT NULL DEFAULT NULL,
  `day` INT NULL DEFAULT NULL,
  INDEX `content_id` (`content_id` ASC) VISIBLE,
  INDEX `plan_id` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `course_ibfk_1`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `course_ibfk_2`
    FOREIGN KEY (`plan_id`)
    REFERENCES `enjoytrip`.`plan` (`plan_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`follow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`follow` (
  `follower` INT NOT NULL,
  `followee` INT NOT NULL,
  PRIMARY KEY (`follower`, `followee`),
  INDEX `followee` (`followee` ASC) VISIBLE,
  CONSTRAINT `follow_ibfk_1`
    FOREIGN KEY (`follower`)
    REFERENCES `enjoytrip`.`users` (`user_id`),
  CONSTRAINT `follow_ibfk_2`
    FOREIGN KEY (`followee`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`memo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`memo` (
  `memo_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `plan_id` INT NULL DEFAULT NULL,
  `content_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`memo_id`),
  INDEX `memo_to_user_user_id_FK_idx` (`user_id` ASC) VISIBLE,
  INDEX `memo_to_plan_plan_id_FK_idx` (`plan_id` ASC) VISIBLE,
  INDEX `memo_to_attraction_info_content_id_FK_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `memo_to_attraction_info_content_id_FK`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `memo_to_plan_plan_id_FK`
    FOREIGN KEY (`plan_id`)
    REFERENCES `enjoytrip`.`plan` (`plan_id`),
  CONSTRAINT `memo_to_user_user_id_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`notice` (
  `notice_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `subject` VARCHAR(30) NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `regist_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `notice_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`notify`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`notify` (
  `notify_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,
  `read` TINYINT(1) NULL DEFAULT NULL,
  `sender_id` INT NULL DEFAULT NULL,
  `target_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`notify_id`),
  INDEX `notify_to_user_user_id_FK_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `notify_to_user_user_id_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plan_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plan_group` (
  `plan_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`plan_id`, `user_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `plan_group_ibfk_1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `enjoytrip`.`plan` (`plan_id`),
  CONSTRAINT `plan_group_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(30) NULL DEFAULT NULL,
  `content` MEDIUMTEXT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `regist_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `plan_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `review_to_user_user_id_FK_idx` (`user_id` ASC) VISIBLE,
  INDEX `review_to_plan_plan_id_FK_idx` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `review_to_plan_plan_id_FK`
    FOREIGN KEY (`plan_id`)
    REFERENCES `enjoytrip`.`plan` (`plan_id`),
  CONSTRAINT `review_to_user_user_id_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`rating` (
  `user_id` INT NOT NULL,
  `review_id` INT NOT NULL,
  `rate` INT NOT NULL,
  PRIMARY KEY (`user_id`, `review_id`),
  INDEX `rating_ibfk_2_idx` (`review_id` ASC) VISIBLE,
  CONSTRAINT `rating_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`),
  CONSTRAINT `rating_ibfk_2`
    FOREIGN KEY (`review_id`)
    REFERENCES `enjoytrip`.`review` (`review_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`wish`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`wish` (
  `user_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `content_id`),
  INDEX `content_id` (`content_id` ASC) VISIBLE,
  CONSTRAINT `wish_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`users` (`user_id`),
  CONSTRAINT `wish_ibfk_2`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
