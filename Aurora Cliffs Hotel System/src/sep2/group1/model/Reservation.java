package sep2.group1.model;

import java.time.LocalDate;

public class Reservation {

  private int reservationNumber;
  private int roomNumber;
  private String email;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private String status;
  private int numberOfGuests;

  public Reservation(int reservationNumber, int roomNumber, String email,
      LocalDate checkIn, LocalDate checkOut,
      String status, int numberOfGuests) {

    this.reservationNumber = reservationNumber;
    this.roomNumber = roomNumber;
    this.email = email;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.status = status;
    this.numberOfGuests = numberOfGuests;
  }

  public int getReservationNumber() {
    return reservationNumber;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getEmail() {
    return email;
  }

  public LocalDate getCheckIn() {
    return checkIn;
  }

  public LocalDate getCheckOut() {
    return checkOut;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }
}