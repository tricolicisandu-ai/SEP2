package sep2.group1.view.ReservationView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.*;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ReservationViewModel;

import java.time.LocalDate;

public class ReservationController {

  private Room room;

  private LocalDate checkInDate;
  private LocalDate checkOutDate;

  @FXML private TextField textFieldFirstName;
  @FXML private TextField textFieldLastName;
  @FXML private TextField textFieldEmail;
  @FXML private CheckBox checkBox;

  public void setRoom(Room room) {
    this.room = room;
    System.out.println("Selected room: " + room.getRoomNumber());
  }

  public void setDates(LocalDate checkIn, LocalDate checkOut) {
    this.checkInDate = checkIn;
    this.checkOutDate = checkOut;
  }

  @FXML
  private void onReserve() {

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

    Guest guest = new Guest(
        textFieldFirstName.getText(),
        textFieldLastName.getText(),
        textFieldEmail.getText()
    );

    // 🔥 ROOM STATE CHANGE
    room.reserve(guest);

    // 🔥 RESERVATION (SPRÁVNE DÁTUMY)
    Reservation reservation = new Reservation(
        (int)(Math.random() * 100000),
        room.getRoomNumber(),
        guest.getEmail(),
        checkInDate,
        checkOutDate,
        "Reserved"
    );

    ReservationManager.addReservation(reservation);

    System.out.println("Reservation created!");

    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
    System.out.println("checkInDate = " + checkInDate);
    System.out.println("checkOutDate = " + checkOutDate);
  }

  @FXML
  private void onCancel() {
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setContentText(msg);
    alert.showAndWait();
  }

  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel)
  {
  }
}