package sep2.group1.view.ReservationView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.Room;
import sep2.group1.view.RoomDetailsView.RoomDetailsController;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ReservationViewModel;

import java.time.LocalDate;

public class ReservationController {
  @FXML private TextField textFieldFirstName;
  @FXML private TextField textFieldLastName;
  @FXML private TextField textFieldEmail;
  @FXML private CheckBox checkBox;

  private ViewHandler viewHandler;
  private ReservationViewModel viewModel;
  private RoomDetailsController parentController;

  private Room room;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private int numberOfGuests;

  public void init(ViewHandler viewHandler, ReservationViewModel viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
  }

  // Tieto metódy volá ViewHandler pri otváraní okna
  public void setRoom(Room selectedRoom) {
    this.room = selectedRoom;
  }

  public void setDates(LocalDate checkIn, LocalDate checkOut) {
    this.checkInDate = checkIn;
    this.checkOutDate = checkOut;
  }

  public void setNumberOfGuests(int numberOfGuests) {
    this.numberOfGuests = numberOfGuests;
  }

  public void setParentController(RoomDetailsController roomDetailsController) {
    this.parentController = roomDetailsController;
  }

  @FXML
  private void onReserve() {
    String first = textFieldFirstName.getText();
    String last = textFieldLastName.getText();
    String email = textFieldEmail.getText();

    // Validácia cez ViewModel
    if (viewModel.isInputInvalid(first, last, email)) {
      showAlert("Please fill all fields correctly!");
      return;
    }
    // Fields input validation
    if (textFieldFirstName.getText().isEmpty() ||
        textFieldLastName.getText().isEmpty() ||
        textFieldEmail.getText().isEmpty()) {
      showAlert("Please fill all fields!");
      return;
    }

    // Email input validation
    if (!email.contains("@") || !email.contains(".")) {
      showAlert("Please enter a valid email address.");
      return;
    }

    // GDPR box must be selected
    if (!checkBox.isSelected()) {
      showAlert("You must agree with GDPR!");
      return;
    }

    // Creating reservation
    viewModel.createReservation(room, first, last, email, checkInDate, checkOutDate, numberOfGuests);

    // Refresh table
    if (parentController != null) {
      parentController.refreshTable();
    }

    // Closing pop-up window
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();

    System.out.println("Reservation created and window closed!");
  }

  @FXML
  private void onCancel() {
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}