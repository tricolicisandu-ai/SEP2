package sep2.group1.model;

public class OccupiedState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Room is already occupied.");
  }

  @Override
  public void checkIn(Room room) {
    System.out.println("Already checked-in.");
  }

  @Override
  public void checkOut(Room room) {
    room.setCurrentGuest(null);
    room.setState(new InMaintenanceState());
  }

  @Override
  public void finishMaintenance(Room room) {
    System.out.println("Room is already occupied.");
  }

  @Override
  public String getName() {
    return "OCCUPIED";
  }
}