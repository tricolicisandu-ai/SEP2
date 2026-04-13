package sep2.group1.model;

public class ReservedState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Room is already reserved.");
  }

  @Override
  public void checkIn(Room room, Guest guest) {
    room.setState(new OccupiedState());
  }

  @Override
  public void checkOut(Room room, Guest guest) {
    room.setCurrentGuest(null);
    room.setState(new AvailableState());
  }

  @Override
  public String getName() {
    return "RESERVED";
  }
}