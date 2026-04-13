package sep2.group1.model;

public class OccupiedState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Room is occupied.");
  }

  @Override
  public void checkIn(Room room, Guest guest) {
    System.out.println("Already occupied.");
  }

  @Override
  public void checkOut(Room room, Guest guest) {
    room.setCurrentGuest(null);
    room.setState(new AvailableState());
  }

  @Override
  public String getName() {
    return "OCCUPIED";
  }
}