package sep2.group1.view.ReservationView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.Guest;
import sep2.group1.model.Room;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ReservationViewModel;

public class ReservationController {

  private Room room;

  @FXML private TextField textFieldFirstName;
  @FXML private TextField textFieldLastName;
  @FXML private TextField textFieldEmail;
  @FXML private CheckBox checkBox;

  public void setRoom(Room room) {
    this.room = room;
    System.out.println("Selected room: " + room.getRoomNumber());
  }

  // 🔥 RESERVE BUTTON
  @FXML
  private void onReserve() {

    // 1. validácia
    if (textFieldFirstName.getText().isEmpty() ||
        textFieldLastName.getText().isEmpty() ||
        textFieldEmail.getText().isEmpty()) {

      showAlert("Please fill all fields!");
      return;
    }

    if (!checkBox.isSelected()) {
      showAlert("You must agree with GDPR!");
      return;
    }

    // 2. vytvor Guest
    Guest guest = new Guest(
        textFieldFirstName.getText(),
        textFieldLastName.getText(),
        textFieldEmail.getText()
    );

    // 3. rezervuj izbu
    room.reserve(guest);

    System.out.println("Room reserved! State: " + room.getState().getName());

    // 4. zatvor okno
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
  }

  // 🔹 CANCEL BUTTON
  @FXML
  private void onCancel() {
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
  }

  // 🔹 ALERT helper
  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setContentText(msg);
    alert.showAndWait();
  }

  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel)
  {
  }

  /*room.setReservationDates(checkInDate, checkOutDate);
room.reserve(guest);*/
}
