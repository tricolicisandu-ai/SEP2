package sep2.group1.client.viewmodel;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import sep2.group1.client.Client;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.server.model.Reservation;

import java.io.IOException;

public class MaidViewModel {

  private ViewHandler viewHandler;

  private final Client client =
      Client.getInstance();

  private ObservableList<Reservation> reservations;

  private FilteredList<Reservation>
      filteredReservations;

  public MaidViewModel(
      ViewHandler viewHandler) {

    this.viewHandler = viewHandler;

    try {

      client.connect();

      reservations =
          client.getReservations();

    }
    catch (IOException e) {
      e.printStackTrace();
    }

    filteredReservations =
        new FilteredList<>(
            reservations,
            r -> "In Maintenance"
                .equalsIgnoreCase(
                    r.getStatus())
        );
    client.addEventHandler(msg -> {

      if (msg.equals("RESERVATION_CHANGED")) {

        javafx.application.Platform.runLater(() -> {
          refreshReservations();
        });
      }
    });
  }

  // ---------------- GET TABLE DATA ----------------
  public FilteredList<Reservation>
  getReservations() {

    return filteredReservations;
  }

  // ---------------- DONE CLEANING ----------------
  public void cleanRoom(
      Reservation reservation) {

    if (reservation != null) {

      client.deleteReservation(
          reservation.getReservationNumber()
      );

      refreshReservations();
    }
  }

  // ---------------- REFRESH ----------------
  public void refreshReservations() {

    reservations.setAll(
        client.getReservations()
    );
  }

  // ---------------- LOGOUT ----------------
  public void logout() {
    viewHandler.openView("main");
  }
}