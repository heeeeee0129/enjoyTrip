CREATE TABLE comment (
    replyNo INT AUTO_INCREMENT PRIMARY KEY,
    article_no INT NOT NULL,
    user_id VARCHAR(16) NOT NULL,
    content TEXT NOT NULL,
    registerTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_no) REFERENCES Board(article_no),
    FOREIGN KEY (user_id) REFERENCES Members(user_id)
);

