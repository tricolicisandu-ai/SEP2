package sep2.group1.client.viewmodel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import sep2.group1.client.Client;
import sep2.group1.server.model.Room;
//import sep2.group1.server.model.RoomManager;
import sep2.group1.server.model.AvailableState;
import sep2.group1.client.view.ViewHandler;
//import sep2.group1.server.model.Reservation;
//import sep2.group1.server.model.ReservationManager;

import java.io.IOException;
import java.time.LocalDate;

public class RoomDetailsViewModel {
  private Room selectedRoom;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private int numberOfGuests;
  private ViewHandler viewHandler;
  private final Client client = Client.getInstance();

  public RoomDetailsViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;

    try {
      client.connect();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Client.getInstance().addEventHandler(msg -> {
      if (msg.equals("RESERVATION_CHANGED")) {
        Platform.runLater(() -> {
          System.out.println("Rooms refresh");
          getAllRooms();
        });
      }
    });
  }

  public void setReservationData(Room selectedRoom, LocalDate checkIn, LocalDate checkOut, int guests) {
    this.selectedRoom = selectedRoom;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.numberOfGuests = guests;
  }

  public ObservableList<Room> getAllRooms() {

    return client.getRooms(
        LocalDate.now(),
        LocalDate.now().plusYears(1)
    );
  }

  public ObservableList<Room> getFilteredRooms(
      Integer guests,
      LocalDate checkIn,
      LocalDate checkOut) {

    if (checkIn == null || checkOut == null) {
      return javafx.collections.FXCollections.observableArrayList();
    }

    ObservableList<Room> rooms =
        client.getRooms(checkIn, checkOut);

    return rooms.filtered(room -> {

      if (guests != null
          && guests > room.getNumberOfBeds()) {
        return false;
      }

      return true;
    });
  }

  public Room getSelectedRoom() { return selectedRoom; }
  public LocalDate getCheckIn() { return checkIn; }
  public LocalDate getCheckOut() { return checkOut; }
  public int getNumberOfGuests() { return numberOfGuests; }

  public void openReservationView(String viewId) {
    viewHandler.openReservationView(viewId);
  }

  public Client getClient()
  {
    return client;
  }
}
