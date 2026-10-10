package org.example.weeksixassignment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRoomRepository implements RoomRepository {

    private final List<Room> rooms = new ArrayList<>(List.of(
            new Room(1L, "Seminar A", 8),
            new Room(2L, "Study Pod", 4),
            new Room(3L, "Rooftop Room", 12)
    ));

    private final AtomicLong nextId = new AtomicLong(4);

    @Override
    public List<Room> findAll() {
        return List.copyOf(rooms);
    }

    @Override
    public Optional<Room> findById(Long id) {
        return rooms.stream()
                .filter(room -> room.id().equals(id))
                .findFirst();
    }

    @Override
    public Room save(Room room) {
        if (room.id() == null) {
            Room newRoom = new Room(
                    nextId.getAndIncrement(),
                    room.name(),
                    room.capacity()
            );

            rooms.add(newRoom);
            return newRoom;
        }

        rooms.removeIf(existing -> existing.id().equals(room.id()));
        rooms.add(room);

        return room;
    }

    @Override
    public boolean deleteById(Long id) {
        return rooms.removeIf(room -> room.id().equals(id));
    }
}
