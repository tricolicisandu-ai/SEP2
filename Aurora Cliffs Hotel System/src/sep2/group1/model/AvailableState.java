package sep2.group1.model;

public class AvailableState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    room.setCurrentGuest(guest);
    room.setState(new ReservedState());
  }

  @Override
  public void checkIn(Room room, Guest guest) {
    room.setCurrentGuest(guest);
    room.setState(new OccupiedState());
  }

  @Override
  public void checkOut(Room room, Guest guest) {
    System.out.println("Room is already available.");
  }

  @Override
  public String getName() {
    return "AVAILABLE";
  }
}