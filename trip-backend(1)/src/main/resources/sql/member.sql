CREATE DATABASE  IF NOT EXISTS `mh`;
use `mh`;

DROP TABLE IF EXISTS `mh`.`members` ;

CREATE TABLE IF NOT EXISTS `mh`.`members` (
  `user_id` VARCHAR(16) NOT NULL,
  `user_name` VARCHAR(20) NOT NULL,
  `user_password` VARCHAR(16) NOT NULL,
  `email_id` VARCHAR(20) NULL DEFAULT NULL,
  `email_domain` VARCHAR(45) NULL DEFAULT NULL,
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sido` VARCHAR(20) NOT NULL,
  `gugun` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into `mh`.`members` (user_id, user_name, user_password, email_id, email_domain, sido, gugun, join_date)
values 	('ssafy', '김싸피', '1234', 'ssafy', 'ssafy.com', '서울광역시', '강남구', now()), 
		('admin', '관리자', '1234', 'admin', 'google.com', '부산광역시', '금정구', now());
        
select * from members;