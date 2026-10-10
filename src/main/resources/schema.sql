CREATE TABLE room (
                      id BIGINT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      capacity INT NOT NULL CHECK (capacity BETWEEN 1 AND 20)
);

CREATE TABLE reservation (
                             id BIGINT PRIMARY KEY,
                             room_id BIGINT NOT NULL,
                             reserved_by VARCHAR(50) NOT NULL,
                             start_time TIMESTAMP NOT NULL,
                             end_time TIMESTAMP NOT NULL,
                             FOREIGN KEY (room_id) REFERENCES room(id)
);