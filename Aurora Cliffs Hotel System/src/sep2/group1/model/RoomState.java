package sep2.group1.model;

public interface RoomState {

  void reserve(Room room, Guest guest);

  void checkIn(Room room, Guest guest);

  void checkOut(Room room, Guest guest);

  String getName();
}
