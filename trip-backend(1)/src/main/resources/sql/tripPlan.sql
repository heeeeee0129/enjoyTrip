CREATE TABLE trip_plan (
	id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(100),
    content TEXT NOT NULL,
	start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Members(user_id) ON DELETE CASCADE
);

CREATE TABLE trip_location (
	id INT AUTO_INCREMENT PRIMARY KEY,
	route_id INT NOT NULL,
    memo TEXT NOT NULL,
    idx INT,
    content_id INT NOT NULL,
    FOREIGN KEY (route_id) REFERENCES Trip_plan(id) ON DELETE CASCADE,
    FOREIGN KEY (content_id) REFERENCES Attraction_info(content_id) ON DELETE CASCADE
);
ALTER TABLE trip_location
ADD COLUMN lat DECIMAL(10, 7) NOT NULL,
ADD COLUMN lng DECIMAL(10, 7) NOT NULL;
Alter table trip_location add column title varchar(100) not null;
select * from trip_plan;

select * from trip_location;

select * from attraction_info;

