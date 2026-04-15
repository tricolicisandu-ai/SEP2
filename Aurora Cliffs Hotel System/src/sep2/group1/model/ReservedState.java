package sep2.group1.model;

public class ReservedState implements RoomState {

  @Override
  public void reserve(Room room, Guest guest) {
    System.out.println("Already reserved.");
  }

  @Override
  public void checkIn(Room room) {
    room.setState(new OccupiedState());
  }

  @Override
  public void checkOut(Room room) {
    System.out.println("Guest not checked in.");
  }

  @Override
  public void finishMaintenance(Room room) {
    System.out.println("Not in maintenance.");
  }

  @Override
  public String getName() {
    return "RESERVED";
  }
}