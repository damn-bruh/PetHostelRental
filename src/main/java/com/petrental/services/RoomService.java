package com.petrental.services;

import com.petrental.models.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final List<Room> rooms = new ArrayList<>();

    public RoomService() {
        // Initialize 20 Small Rooms and 10 Large Rooms
        int id = 1;
        for (int i = 0; i < 20; i++) {
            rooms.add(new Room(id++, "Small Room"));
        }
        for (int i = 0; i < 10; i++) {
            rooms.add(new Room(id++, "Large Room"));
        }
    }

    /**
     * Retrieves all available rooms that match the given type.
     *
     * @param type The type of room to filter by ("Small Room", "Large Room").
     * @return A list of available rooms of the specified type.
     */
    public Room getAvailableRoom(String type) {
        return rooms.stream()
                .filter(room -> room.getType().equalsIgnoreCase(type) && !room.isOccupied())
                .findFirst()
                .orElse(null);
    }

    /**
     * Marks a room as occupied and assigns an animal to it.
     *
     * @param roomId The ID of the room to mark as occupied.
     */
    public void markRoomOccupied(int roomId) {
        rooms.stream()
                .filter(room -> room.getId() == roomId)
                .findFirst()
                .ifPresent(room -> room.setOccupied(true));
    }

    /**
     * Retrieves all available rooms.
     *
     * @return A list of available rooms.
     */
    public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(room -> !room.isOccupied())
                .toList();
    }
}
