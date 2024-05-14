CREATE TABLE comment (
    reply_no INT AUTO_INCREMENT PRIMARY KEY,
    article_no INT NOT NULL,
    user_id VARCHAR(16) NOT NULL,
    content TEXT NOT NULL,
    register_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_no) REFERENCES Board(article_no) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Members(user_id) ON DELETE CASCADE
);
