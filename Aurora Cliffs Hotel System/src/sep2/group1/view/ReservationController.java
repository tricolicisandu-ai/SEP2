package sep2.group1.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sep2.group1.model.Room;
import sep2.group1.viewmodel.ReservationViewModel;

public class ReservationController {

  private Room room;

  public Room room()
  {
    return room;
  }

  @FXML
  private TextField textFieldFirstName;

  @FXML
  private TextField textFieldLastName;

  @FXML
  private TextField textFieldEmail;

  public void setRoom(Room room) {
    this.room = room;

    System.out.println("Selected room: " + room.getRoomNumber());
  }

  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel)
  {
  }
}
