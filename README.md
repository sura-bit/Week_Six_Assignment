## Database

This project uses an H2 in-memory database with two tables: `room` and `reservation`.

### Tables

| Table         | Columns                                                  |
| ------------- | -------------------------------------------------------- |
| `room`        | `id`, `name`, `capacity`                                 |
| `reservation` | `id`, `room_id`, `reserved_by`, `start_time`, `end_time` |

The `reservation.room_id` column is a foreign key referencing `room.id`. One room can have multiple reservations.

The `room.capacity` column has a database constraint requiring the value to be between 1 and 20.

### H2 Console

After starting the application, open:

`http://localhost:8080/h2-console`

Use the following connection settings:

| Setting   | Value                |
| --------- | -------------------- |
| JDBC URL  | `jdbc:h2:mem:roomdb` |
| User Name | `sa`                 |
| Password  | *(empty)*            |

The database is initialized automatically from `schema.sql` and `data.sql` when the application starts.

## How to Run

**JDK:** 21

### macOS / Linux

```bash
./gradlew bootRun
```

### Windows

```bat
gradlew.bat bootRun
```

## AI Use

No AI used.
