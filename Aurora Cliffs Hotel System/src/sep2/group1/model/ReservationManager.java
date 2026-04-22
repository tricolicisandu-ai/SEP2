package sep2.group1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservationManager {

  private static final ObservableList<Reservation> reservations =
      FXCollections.observableArrayList();

  public static ObservableList<Reservation> getReservations() {
    return reservations;
  }

  public static void addReservation(Reservation r) {
    reservations.add(r);
    System.out.println("GLOBAL ADD -> size: " + reservations.size());
  }
}