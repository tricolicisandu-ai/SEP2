package sep2.group1.viewmodel;

import javafx.collections.ObservableList;
import sep2.group1.model.Room;
import sep2.group1.model.RoomManager;
import sep2.group1.model.AvailableState;
import sep2.group1.view.ViewHandler;

import java.time.LocalDate;

public class RoomDetailsViewModel {
  private Room selectedRoom;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private int numberOfGuests;
  private ViewHandler viewHandler;

  public RoomDetailsViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
  }

  public void setReservationData(Room selectedRoom, LocalDate checkIn, LocalDate checkOut, int guests) {
    this.selectedRoom = selectedRoom;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.numberOfGuests = guests;
  }

  public ObservableList<Room> getAllRooms() {
    return RoomManager.getRooms();
  }

  public ObservableList<Room> getFilteredRooms(Integer guests,
      LocalDate checkIn,
      LocalDate checkOut) {

    return RoomManager.getRooms().filtered(room -> {

      if (guests != null && guests > room.getNumberOfBeds())
        return false;

      if (!(room.getState() instanceof AvailableState))
        return false;

      if (checkIn != null && checkOut != null) {
        if (room.getCheckInDate() != null && room.getCheckOutDate() != null) {
          boolean overlap =
              !checkOut.isBefore(room.getCheckInDate()) &&
                  !checkIn.isAfter(room.getCheckOutDate());

          if (overlap) return false;
        }
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
}