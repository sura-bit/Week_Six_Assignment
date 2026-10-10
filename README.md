## Database setup

Run as root once (the root password is not stored in the project):

```sql
CREATE DATABASE roomdb;
CREATE USER 'roomapp'@'localhost' IDENTIFIED BY 'roomapp1234';
GRANT ALL PRIVILEGES ON roomdb.* TO 'roomapp'@'localhost';
```

Before testing, reset the tables and sample data. Use either way:

**Terminal** (from the project folder):

```bash
mysql -u roomapp -p roomdb < reset.sql
```

Enter `roomapp1234` when asked for the password.

**MySQL Workbench:**

1. Open a connection as `roomapp` (password `roomapp1234`).
2. Open `reset.sql` in any editor, copy everything, and paste it into a new SQL tab in Workbench.
3. Click the lightning bolt ⚡ (Execute) to run the whole script.
4. Check with `SELECT * FROM roomdb.room;`. It should show 3 rooms. -u roomapp -p roomdb < reset.sql
```

## API contract (run reset.sql first, then api.http in order)

| # | Request | Expected |
|---|---------|----------|
| 1 | GET /api/rooms | 200 |
| 2 | GET /api/rooms?minCapacity=6 | 200 |
| 3 | GET /api/rooms?keyword=pod | 200 |
| 4 | GET /api/rooms?minCapacity=6&keyword=roof | 200 |
| 5 | GET /api/rooms?sort=capacity | 200 |
| 6 | GET /api/rooms/2 | 200 |
| 7 | GET /api/rooms/99 | 404 |
| 8 | PUT /api/rooms/1 | 200 |
| 9 | PUT /api/rooms/1 (capacity 0) | 400 |
| 10 | PUT /api/rooms/99 | 404 |
| 11 | POST /api/rooms | 201 |
| 12 | POST /api/rooms (capacity 25) | 400 |
| 13 | DELETE /api/rooms/3 | 204 |
| 14 | GET /api/rooms/count | 200 |
| 15 | DELETE /api/rooms/1 | 409 |
| 16 | DELETE /api/rooms/99 | 404 |

## 409 Conflict

The web layer returns it: `RoomExceptionHandler` turns the `DataIntegrityViolationException`
from MySQL's foreign-key check into 409, because choosing an HTTP status is the web layer's job,
not the service's or the repository's.

## PUT

```
Hibernate: 
    select
        r1_0.id,
        r1_0.capacity,
        r1_0.name 
    from
        room r1_0 
    where
        r1_0.id=?
Hibernate: 
    update
        room 
    set
        capacity=?,
        name=? 
    where
        id=?
```

Hibernate first SELECTs room 1 with `findById`, and because the service changes that loaded entity
inside a `@Transactional` method, dirty checking sends one UPDATE when the transaction commits,
without calling `save`.

Hibernate first SELECTs the room by id, and because the service changes that loaded entity inside
a @Transactional method, dirty checking sends one UPDATE when the transaction commits.

## AI use

Used Claude for README file writing.