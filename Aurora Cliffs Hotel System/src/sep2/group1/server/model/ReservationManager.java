package sep2.group1.server.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep2.group1.server.persistence.ReservationDAO;

import java.util.List;

public class ReservationManager {

  private static final ObservableList<Reservation> reservations =
      FXCollections.observableArrayList();

  public static ObservableList<Reservation> getReservations() {
    return reservations;
  }

  public static void addReservation(Reservation r) {
    reservations.add(r);
    System.out.println("Reservation -> size: " + reservations.size());
  }

  public static void loadFromDB(List<Reservation> list) {
    reservations.setAll(list);
  }

  public static void refreshReservations() {

    ReservationDAO dao = new ReservationDAO();

    reservations.setAll(dao.getAllReservations());

    System.out.println("Reservations refreshed: " + reservations.size());
  }
}