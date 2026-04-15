package sep2.group1.model;

public class Room {

  private int roomNumber;
  private String roomType;
  private int numberOfBeds;
  private double price;
  private int numberOfGuest;
  private Date date;

  private RoomState state;
  private Guest currentGuest;

  public Room(int roomNumber, String roomType, int numberOfBeds, double price, int numberOfGuest) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.numberOfBeds = numberOfBeds;
    this.price = price;
    this.numberOfGuest = numberOfGuest;

    this.state = new AvailableState(); // default
  }

  // STATE delegation
  public void reserve(Guest guest) {
    state.reserve(this, guest);
  }

  public void checkIn(Guest guest) {
    state.checkIn(this);
  }

  public void checkOut(Guest guest) {
    state.checkOut(this);
  }

  // getters/setters
  public RoomState getState() {
    return state;
  }

  public void setState(RoomState state) {
    this.state = state;
  }

  public Guest getCurrentGuest() {
    return currentGuest;
  }

  public void setCurrentGuest(Guest currentGuest) {
    this.currentGuest = currentGuest;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getRoomType()
  {
    return roomType;
  }

  public int getNumberOfBeds() {
    return numberOfBeds;
  }

  public double getPrice() {
    return price;
  }

  public int getNumberOfGuest() {
    return numberOfGuest;
  }
}