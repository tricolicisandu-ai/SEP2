package sep2.group1.client.viewmodel;

import sep2.group1.server.model.*;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.server.persistence.ReservationDAO;

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

  public int createReservation(Room room,
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

    //ReservationManager.addReservation(reservation);
    ReservationDAO dao = new ReservationDAO();
    int databaseId = dao.createReservation(new Reservation(
        0,
        room.getRoomNumber(),
        guest.getEmail(),
        checkIn,
        checkOut,
        "Reserved",
        guests,
        firstName,
        lastName
    ));
    ReservationManager.refreshReservations();

    return databaseId;
  }
}