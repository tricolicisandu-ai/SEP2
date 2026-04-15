package sep2.group1.model;

public interface RoomState {

  void reserve(Room room, Guest guest);

  void checkIn(Room room);

  void checkOut(Room room);

  void finishMaintenance(Room room);

  String getName();
}
