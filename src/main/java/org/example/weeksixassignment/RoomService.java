package org.example.weeksixassignment;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> search(
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(defaultValue = " ") String keyword,
            @RequestParam(defaultValue = " ") String sort) {

        List<Room> result = roomRepository.findAll().stream()
                .filter(room ->
                        minCapacity == null ||
                                room.capacity() >= minCapacity)
                .filter(room ->
                        keyword == null ||
                                room.name().toLowerCase()
                                        .contains(keyword.toLowerCase()))
                .toList();

        if ("capacity".equals(sort)) {
            return result.stream()
                    .sorted(Comparator.comparing(Room::capacity))
                    .toList();
        }

        if ("name".equals(sort)) {
            return result.stream()
                    .sorted(Comparator.comparing(Room::name))
                    .toList();
        }

        return result;
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Room create(String name, int capacity) {

        checkCapacity(capacity);

        return roomRepository.save(
                new Room(null, name, capacity)
        );
    }

    public Optional<Room> update(
            Long id,
            String name,
            int capacity) {

        checkCapacity(capacity);

        return roomRepository.findById(id)
                .map(existing -> roomRepository.save(
                        new Room(id, name, capacity)
                ));
    }

    public boolean delete(Long id) {
        return roomRepository.deleteById(id);
    }

    public void checkCapacity(int capacity) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException(
                    "Capacity must be between 1 and 20"
            );
        }
    }

}
