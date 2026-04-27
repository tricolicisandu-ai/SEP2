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
  private int numberOfGuests;

  @FXML private TextField textFieldFirstName;
  @FXML private TextField textFieldLastName;
  @FXML private TextField textFieldEmail;
  @FXML private CheckBox checkBox;

  public void setRoom(Room room) {
    this.room = room;
    if (room != null) {
      System.out.println("Selected room set in Controller: " + room.getRoomNumber());
    }
  }

  public void setDates(LocalDate checkIn, LocalDate checkOut) {
    this.checkInDate = checkIn;
    this.checkOutDate = checkOut;
  }

  public void setNumberOfGuests(int numberOfGuests) {
    this.numberOfGuests = numberOfGuests;
  }

  @FXML
  private void onReserve() {
    // Room must be selected first
    if (room == null) {
      System.err.println("CRITICAL ERROR: 'room' object is null in ReservationController!");
      showAlert("Error: Room data is missing. Please select a room first.");
      return;
    }

    // Validate input fields
    if (textFieldFirstName.getText().isEmpty() ||
        textFieldLastName.getText().isEmpty() ||
        textFieldEmail.getText().isEmpty()) {
      showAlert("Please fill all fields!");
      return;
    }

    // GDPR box must be selected
    if (!checkBox.isSelected()) {
      showAlert("You must agree with GDPR!");
      return;
    }

    // Guest information
    Guest guest = new Guest(
        textFieldFirstName.getText(),
        textFieldLastName.getText(),
        textFieldEmail.getText()
    );

    room.reserve(guest);

    // Create a reservation info
    int resNum = (int)(Math.random() * 100000);
    Reservation reservation = new Reservation(
        resNum,
        room.getRoomNumber(),
        guest.getEmail(),
        checkInDate,
        checkOutDate,
        "Reserved",
        numberOfGuests
    );

    ReservationManager.addReservation(reservation);

    // Confirmation alert
    showSuccessAlert(guest, room.getRoomNumber(), resNum);

    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();

    System.out.println("Reservation was created and confirmed!");
  }

  @FXML
  private void onCancel() {
    Stage stage = (Stage) textFieldFirstName.getScene().getWindow();
    stage.close();
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }

  private void showSuccessAlert(Guest guest, int roomNumber, int reservationNumber) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Reservation Confirmed");
    alert.setHeaderText("Thank you for your reservation!");

    String info = String.format("Name: " + guest.getFirstName() + " " + guest.getLastName() +
        "\n" + "Email: " + guest.getEmail() +
        "\n" + "Room Number: " + roomNumber +
        "\n" + "Reservation Number: " + reservationNumber);

    alert.setContentText(info);
    alert.showAndWait();
  }

  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel) {

  }
}