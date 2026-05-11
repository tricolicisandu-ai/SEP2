
package sep2.group1.client.viewmodel;

import sep2.group1.client.Client;
import sep2.group1.server.model.Room;
import sep2.group1.client.view.ViewHandler;

import java.io.IOException;
import java.time.LocalDate;

public class ReservationViewModel {


  private final Client client = Client.getInstance();
  public ReservationViewModel(ViewHandler viewHandler) {

    try {
      client.connect();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isInputInvalid(String first,
      String last,
      String email) {

    return first.isEmpty()
        || last.isEmpty()
        || email.isEmpty();
  }

  public boolean isGdprAccepted(boolean accepted) {
    return accepted;
  }

  public int createReservation(Room room,
      String firstName,
      String lastName,
      String email,
      LocalDate checkIn,
      LocalDate checkOut,
      int guests) {

    client.reserveRoom(
        room.getRoomNumber(),
        firstName,
        lastName,
        email,
        checkIn,
        checkOut,
        guests
    );

    return guests;
  }
}