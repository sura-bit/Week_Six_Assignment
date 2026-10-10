-- Q-1
SELECT * FROM room
WHERE capacity >= 6
ORDER BY capacity DESC;

--Q-2
SELECT * FROM reservation
WHERE reserved_by = 'Mina';

-- Q-3
SELECT r.id, r.reserved_by, r.start_time, r.end_time, rm.name AS room_name
FROM reservation r
         JOIN room rm ON r.room_id = rm.id;

-- Q-4
SELECT r.*
FROM reservation r
         JOIN room rm ON r.room_id = rm.id
WHERE rm.name = 'Seminar A'
  AND r.start_time >= '2026-10-06 00:00:00'
  AND r.start_time < '2026-10-07 00:00:00';

-- Q-5
SELECT rm.name AS room_name, COUNT(r.id) AS reservation_count
FROM room rm
         JOIN reservation r ON rm.id = r.room_id
GROUP BY rm.id, rm.name;

-- Q-6
SELECT rm.name AS room_name, COUNT(r.id) AS reservation_count
FROM room rm
         LEFT JOIN reservation r ON rm.id = r.room_id
GROUP BY rm.id, rm.name;

-- Q-7
SELECT rm.*
FROM room rm
         LEFT JOIN reservation r ON rm.id = r.room_id
WHERE r.id IS NULL;


-- Q-8
SELECT rm.name AS room_name, COUNT(r.id) AS reservation_count
FROM room rm
         JOIN reservation r ON rm.id = r.room_id
GROUP BY rm.id, rm.name
HAVING COUNT(r.id) > 2;
