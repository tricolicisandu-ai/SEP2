package sep2.group1.viewmodel;

import sep2.group1.model.*;
import sep2.group1.view.ViewHandler;

import java.time.LocalDate;

public class ReservationViewModel
{
  public ReservationViewModel(ViewHandler viewHandler)
  {
  }

  public boolean isInputInvalid(String first, String last, String email)
  {
    return first.isEmpty() || last.isEmpty() || email.isEmpty();
  }

  public boolean isGdprAccepted(boolean accepted)
  {
    return accepted;
  }

  public void createReservation(Room room,
      String firstName,
      String lastName,
      String email,
      LocalDate checkIn,
      LocalDate checkOut,
      int guests)
  {
    Guest guest = new Guest(firstName, lastName, email);

    // reserve room
    room.reserve(guest);

    Reservation reservation = new Reservation(
        (int)(Math.random() * 100000),
        room.getRoomNumber(),
        guest.getEmail(),
        checkIn,
        checkOut,
        "Reserved",
        guests
    );

    ReservationManager.addReservation(reservation);
  }
}