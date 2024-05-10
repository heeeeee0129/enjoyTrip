CREATE TABLE IF NOT EXISTS `slang` (
  `slang_no` INT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`slang_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


INSERT INTO slang (word)
VALUES 
("ㅅㅂ"),
("시발"),
("병신"),
("ㅂㅅ"),
("미친새끼"),
("존나"),
("ㅈㄴ"),
("ㅈㄹ"),
("지랄"),
("씹"),
("니미"),
("니애미"),
("병신"),
("ㅂㅅ"),
("개새끼"),
("씨빨"),
("닥쳐"),
("또라이"),
("걸레"),
("좆밥");

SELECT *
FROM slang;
