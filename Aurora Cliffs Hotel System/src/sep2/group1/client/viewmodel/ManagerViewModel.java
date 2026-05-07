package sep2.group1.client.viewmodel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import sep2.group1.server.model.Reservation;
import sep2.group1.server.model.ReservationManager;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.server.persistence.ReservationDAO;

public class ManagerViewModel {

  private ViewHandler viewHandler;

  /*private ObservableList<Reservation> reservations;
  private FilteredList<Reservation> filteredReservations;

  public ManagerViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
   this.reservations = ReservationManager.getReservations();
    this.filteredReservations = new FilteredList<>(reservations, p -> true);
  }*/
  private ReservationDAO reservationDAO = new ReservationDAO();
  private ObservableList<Reservation> reservations;
  private FilteredList<Reservation> filteredReservations;

  public ManagerViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;

    this.reservations = reservationDAO.getAllReservations();
    this.filteredReservations = new FilteredList<>(reservations, p -> true);
  }

  // TABLE DATA
  public FilteredList<Reservation> getReservations() {
    return filteredReservations;
  }

  // CHECK-IN
  public void checkIn(Reservation r) {
    // Check-in allowed only after "Reserved" status
    if (r != null && "Reserved".equalsIgnoreCase(r.getStatus())) {
      r.setStatus("Occupied");
    }
  }

  // CHECK-OUT
  public void checkOut(Reservation r) {

    if (r != null && "Occupied".equalsIgnoreCase(r.getStatus())) {

      r.setStatus("In Maintenance");

      Thread t = new Thread(() -> {
        try {

          Thread.sleep(3000);

          ReservationDAO dao = new ReservationDAO();
          dao.deleteReservation(r.getReservationNumber());
          ReservationManager.refreshReservations();
          ReservationManager.loadFromDB(dao.getAllReservations());
          Platform.runLater(() -> {
            reservations.remove(r);
          });

        } catch (InterruptedException e) {
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

      ReservationDAO dao = new ReservationDAO();

      // delete from DB
      dao.deleteReservation(r.getReservationNumber());

      // reload reservations from DB
      ReservationManager.refreshReservations();

      // refresh table list
      reservations.setAll(ReservationManager.getReservations());
    }
  }

  // SEARCH LOGIC
  public void search(String resNo, String roomNo, String email) {

    filteredReservations.setPredicate(r -> {

      if ((resNo == null || resNo.isEmpty()) &&
          (roomNo == null || roomNo.isEmpty()) &&
          (email == null || email.isEmpty())) {
        return true;
      }

      boolean isResNo = resNo == null || resNo.isEmpty()
          || String.valueOf(r.getReservationNumber()).contains(resNo);

      boolean isRoomNo = roomNo == null || roomNo.isEmpty()
          || String.valueOf(r.getRoomNumber()).contains(roomNo);

      boolean isEmail = email == null || email.isEmpty()
          || r.getEmail().toLowerCase().contains(email.toLowerCase());

      return isResNo && isRoomNo && isEmail;
    });
  }

  // LOGOUT NAVIGATION
  public void logout() {
    viewHandler.openView("main");
  }
}