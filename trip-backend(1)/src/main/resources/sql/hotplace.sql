CREATE TABLE hotplace (
	hot_no INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(16) NOT NULL,
	place_name VARCHAR(100) NULL DEFAULT NULL,
    category VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    hit INT NULL DEFAULT 0,
    register_time TIMESTAMP NOT NULL,
    latitude DECIMAL(20,17) NOT NULL,
    longitude  DECIMAL(20,17) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Members(user_id) ON DELETE CASCADE
);
ALTER TABLE hotplace
ADD COLUMN like_count INT DEFAULT 0;

CREATE TABLE file_info (
	file_no INT AUTO_INCREMENT PRIMARY KEY,
	hot_no INT NOT NULL,
	save_folder VARCHAR(1000) NOT NULL,
    original_file VARCHAR(1000) NOT NULL,
    save_file VARCHAR(1000) NOT NULL,
    FOREIGN KEY (hot_no) REFERENCES Hotplace(hot_no) ON DELETE CASCADE
);
