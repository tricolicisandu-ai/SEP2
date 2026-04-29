package sep2.group1.viewmodel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import sep2.group1.model.Reservation;
import sep2.group1.model.ReservationManager;
import sep2.group1.view.ViewHandler;

public class ManagerViewModel {

  private ViewHandler viewHandler;

  private ObservableList<Reservation> reservations;
  private FilteredList<Reservation> filteredReservations;

  public ManagerViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
    this.reservations = ReservationManager.getReservations();
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
    // Check-out allowed only after "Occupied" status
    if (r != null && "Occupied".equalsIgnoreCase(r.getStatus())) {
      r.setStatus("In Maintenance");

      Thread t = new Thread(() -> {
        try {
          Thread.sleep(3000);
          Platform.runLater(() -> reservations.remove(r));
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
      r.setStatus("Available");
      reservations.remove(r);
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