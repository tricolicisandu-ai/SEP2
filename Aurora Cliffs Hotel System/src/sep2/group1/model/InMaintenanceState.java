package sep2.group1.model;

public class InMaintenanceState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Room is in maintenance.");
  }

  @Override
  public void checkIn(Room room, Guest guest) {
    System.out.println("Room is in maintenance.");
  }

  @Override
  public void checkOut(Room room, Guest guest) {
    room.setState(new AvailableState());
  }

  @Override
  public String getName() {
    return "IN_MAINTENANCE";
  }
}