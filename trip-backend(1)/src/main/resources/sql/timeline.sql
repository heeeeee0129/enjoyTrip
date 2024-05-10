use mh;
drop table timeline;
CREATE TABLE IF NOT EXISTS `mh`.`timeline` (
  `timeline_no` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(16) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL,
  `contentId` INT NULL,
  PRIMARY KEY (`timeline_no`),
  INDEX `timeline_to_attractioninfo_contentid_fk_idx` (`contentId` ASC) VISIBLE,
  CONSTRAINT `timeline_to_members_userid_fk`
    FOREIGN KEY (`userId`)
    REFERENCES `mh`.`members` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `timeline_to_attractioninfo_contentid_fk`
    FOREIGN KEY (`contentId`)
    REFERENCES `mh`.`attraction_info` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `timeline`
ADD CONSTRAINT `fk_userId`
FOREIGN KEY (`userId`) REFERENCES `members`(`user_id`),
ADD CONSTRAINT `fk_contentId`
FOREIGN KEY (`contentId`) REFERENCES `attraction_info`(`content_id`);




