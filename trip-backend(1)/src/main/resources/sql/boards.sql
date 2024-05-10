
DROP TABLE IF EXISTS `notice` ;

CREATE TABLE IF NOT EXISTS `mh`.`notice` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `notice_to_members_userid_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `notice_to_members_userid_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `mh`.`members` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


INSERT INTO notice (user_id, subject, content, hit, register_time) VALUES
('admin', '첫 번째 공지사항', '이것은 첫 번째 게시물입니다.', 10, NOW()),
('admin', '두 번째 공지사항', '이것은 두 번째 게시물입니다.', 15, NOW()),
('admin', '세 번째 게시물', '이것은 세 번째 게시물입니다.', 20, NOW());

commit;


DROP TABLE IF EXISTS `board` ;

CREATE TABLE IF NOT EXISTS `mh`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `board_to_members_userid_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_members_userid_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `mh`.`members` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


INSERT INTO board (user_id, subject, content, hit, register_time)
VALUES ('ssafy', '제목1', '내용1', 0, NOW()),
       ('ssafy', '제목2', '내용2', 0, NOW()),
       ('ssafy', '제목3', '내용3', 0, NOW()),
       ('ssafy', '제목4', '내용4', 0, NOW()),
       ('ssafy', '제목5', '내용5', 0, NOW()),
       ('ssafy', '제목6', '내용6', 0, NOW()),
       ('ssafy', '제목7', '내용7', 0, NOW()),
       ('ssafy', '제목8', '내용8', 0, NOW()),
       ('ssafy', '제목9', '내용9', 0, NOW());

COMMIT;
