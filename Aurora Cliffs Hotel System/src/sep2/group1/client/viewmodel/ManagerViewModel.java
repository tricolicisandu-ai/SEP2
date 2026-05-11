package sep2.group1.client.viewmodel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import sep2.group1.client.Client;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.server.model.Reservation;

import java.io.IOException;

public class ManagerViewModel {

  private ViewHandler viewHandler;

  private final Client client = Client.getInstance();

  private ObservableList<Reservation> reservations;
  private FilteredList<Reservation> filteredReservations;

  public ManagerViewModel(ViewHandler viewHandler) {

    this.viewHandler = viewHandler;

    try {

      client.connect();

      this.reservations =
          client.getReservations();

    }
    catch (IOException e) {
      e.printStackTrace();
    }

    this.filteredReservations =
        new FilteredList<>(reservations, p -> true);

    Client.getInstance().addEventHandler(msg -> {
      if (msg.equals("RESERVATION_CHANGED")) {
        refreshReservations();
      }
    });
  }

  // TABLE DATA
  public FilteredList<Reservation> getReservations() {
    return filteredReservations;
  }

  public void refreshReservations() {
    try {
      reservations.setAll(client.getReservations());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // CHECK-IN
  public void checkIn(Reservation r) {

    if (r != null
        && "Reserved".equalsIgnoreCase(r.getStatus())) {

      r.setStatus("Occupied");
    }
  }

  // CHECK-OUT
  public void checkOut(Reservation r) {

    if (r != null
        && "Occupied".equalsIgnoreCase(r.getStatus())) {

      r.setStatus("In Maintenance");

      Thread t = new Thread(() -> {

        try {

          Thread.sleep(3000);

          client.deleteReservation(
              r.getReservationNumber());

          Platform.runLater(() -> {
            reservations.remove(r);
          });

        }
        catch (Exception e) {
          e.printStackTrace();
        }
      });

      t.setDaemon(true);
      t.start();
    }
  }

  // CANCEL
  public void cancel(Reservation r) {

    if (r != null) {

      client.deleteReservation(
          r.getReservationNumber());

      reservations.remove(r);

    }
  }

  // SEARCH LOGIC
  public void search(String resNo,
      String roomNo,
      String email) {

    filteredReservations.setPredicate(r -> {

      if ((resNo == null || resNo.isEmpty()) &&
          (roomNo == null || roomNo.isEmpty()) &&
          (email == null || email.isEmpty())) {

        return true;
      }

      boolean isResNo =
          resNo == null
              || resNo.isEmpty()
              || String.valueOf(
                  r.getReservationNumber())
              .contains(resNo);

      boolean isRoomNo =
          roomNo == null
              || roomNo.isEmpty()
              || String.valueOf(
                  r.getRoomNumber())
              .contains(roomNo);

      boolean isEmail =
          email == null
              || email.isEmpty()
              || r.getEmail()
              .toLowerCase()
              .contains(email.toLowerCase());

      return isResNo
          && isRoomNo
          && isEmail;
    });
  }

  // LOGOUT NAVIGATION
  public void logout() {
    viewHandler.openView("main");
  }
}