package sep2.group1.model;

public class InMaintenanceState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Room under maintenance.");
  }

  @Override
  public void checkIn(Room room) {
    System.out.println("Room under maintenance.");
  }

  @Override
  public void checkOut(Room room) {
    System.out.println("Already checked out.");
  }

  @Override
  public void finishMaintenance(Room room) {
    room.setState(new AvailableState());
  }

  @Override
  public String getName() {
    return "IN_MAINTENANCE";
  }
}
