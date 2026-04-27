package sep2.group1.model;

import java.time.LocalDate;
import java.util.Random;

public class Room {

  private int roomNumber;
  private String roomType;
  private int numberOfBeds;
  private double price;
  private int numberOfGuest;

  private int reservationNumber;

  private LocalDate checkInDate;
  private LocalDate checkOutDate;

  private RoomState state;
  private Guest currentGuest;

  public Room(int roomNumber, String roomType, int numberOfBeds, double price, int numberOfGuest) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.numberOfBeds = numberOfBeds;
    this.price = price;
    this.numberOfGuest = numberOfGuest;
    this.state = new AvailableState();
  }

  public void reserve(Guest guest) {
    Random random = new Random();
    this.reservationNumber = 100000 + random.nextInt(900000);
    this.currentGuest = guest;
    state.reserve(this, guest);
  }

  public void checkIn(Guest guest) {
    state.checkIn(this);
  }

  public void checkOut(Guest guest) {
    state.checkOut(this);
  }

  public RoomState getState() {
    return state;
  }

  public void setState(RoomState state) {
    this.state = state;
  }

  public Guest getCurrentGuest() {
    return currentGuest;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getRoomType() {
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

  public LocalDate getCheckInDate() {
    return checkInDate;
  }

  public LocalDate getCheckOutDate() {
    return checkOutDate;
  }

  public void setReservationDates(LocalDate checkIn, LocalDate checkOut) {
    this.checkInDate = checkIn;
    this.checkOutDate = checkOut;
  }

  public int getReservationNumber() {
    return reservationNumber;
  }

  public void setCurrentGuest(Object o)
  {

  }
}