package sep2.group1.model;

public class AvailableState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    room.setCurrentGuest(guest);
    room.setState(new ReservedState());
  }

  @Override
  public void checkIn(Room room) {
    System.out.println("No reservation.");
  }

  @Override
  public void checkOut(Room room) {
    System.out.println("Room is already free.");
  }

  @Override
  public void finishMaintenance(Room room) {
    System.out.println("Not in maintenance.");
  }

  @Override
  public String getName() {
    return "AVAILABLE";
  }
}