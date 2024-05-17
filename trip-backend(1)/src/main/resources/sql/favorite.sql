CREATE TABLE favorite (
	favorite_no INT AUTO_INCREMENT PRIMARY KEY,
	hot_no INT NOT NULL,
    user_id VARCHAR(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Members(user_id) ON DELETE CASCADE,
    FOREIGN KEY (hot_no) REFERENCES Hotplace(hot_no) ON DELETE CASCADE
);