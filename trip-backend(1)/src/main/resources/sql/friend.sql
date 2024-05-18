CREATE TABLE friend (
	friend_no INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(16) NOT NULL,
	friend_id VARCHAR(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Members(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES Members(user_id) ON DELETE CASCADE
);

INSERT INTO friend (user_id, friend_id)
VALUES ("kong", "ssafy"), ("ssafy","kong");

select * from friend;

delete from friend;

		SELECT f.friend_no, f.user_id, f.friend_id, m.user_name
		FROM friend f
		JOIN members m ON f.friend_id = m.user_name
		WHERE f.user_id = "ssafy"
		ORDER BY f.friend_no ASC