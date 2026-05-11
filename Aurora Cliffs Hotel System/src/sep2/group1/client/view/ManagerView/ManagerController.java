package sep2.group1.client.view.ManagerView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.client.Client;
import sep2.group1.server.model.Reservation;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.ManagerViewModel;

import java.time.LocalDate;
import java.util.Optional;

public class ManagerController {

  @FXML private TableView<Reservation> roomsTable;

  @FXML private TableColumn<Reservation, Integer> colReservationNumber;
  @FXML private TableColumn<Reservation, Integer> colRoomNumber;
  @FXML private TableColumn<Reservation, String> colEmail;
  @FXML private TableColumn<Reservation, LocalDate> colCheckIn;
  @FXML private TableColumn<Reservation, LocalDate> colCheckOut;
  @FXML private TableColumn<Reservation, String> colStatus;

  @FXML private TextField reservationNumberTextField;
  @FXML private TextField roomNumberTextField;
  @FXML private TextField emailTextField;

  private ManagerViewModel viewModel;
  private boolean listenerAdded = false;

  @FXML
  public void initialize() {
    // UI only setup, data comes later in init()
  }

  public void init(ViewHandler viewHandler, ManagerViewModel viewModel) {

    this.viewModel = viewModel;

    roomsTable.setItems(viewModel.getReservations());

    colReservationNumber.setCellValueFactory(data ->
        new javafx.beans.property.SimpleIntegerProperty(
            data.getValue().getReservationNumber()
        ).asObject()
    );

    colRoomNumber.setCellValueFactory(data ->
        new javafx.beans.property.SimpleIntegerProperty(
            data.getValue().getRoomNumber()
        ).asObject()
    );

    colEmail.setCellValueFactory(data ->
        new javafx.beans.property.SimpleStringProperty(
            data.getValue().getEmail()
        )
    );

    colStatus.setCellValueFactory(data ->
        new javafx.beans.property.SimpleStringProperty(
            data.getValue().getStatus()
        )
    );

    colCheckIn.setCellValueFactory(data ->
        new javafx.beans.property.SimpleObjectProperty<>(
            data.getValue().getCheckIn()
        )
    );

    colCheckOut.setCellValueFactory(data ->
        new javafx.beans.property.SimpleObjectProperty<>(
            data.getValue().getCheckOut()
        )
    );

    reservationNumberTextField.textProperty().addListener((obs, old, newVal) -> onSearch());
    roomNumberTextField.textProperty().addListener((obs, old, newVal) -> onSearch());
    emailTextField.textProperty().addListener((obs, old, newVal) -> onSearch());

      if (!listenerAdded) {
        listenerAdded = true;

      /*  Client.getInstance().addEventHandler((String msg) -> {

          if (msg.equals("RESERVATION_CHANGED")) {

            javafx.application.Platform.runLater(() -> {
              viewModel.refreshReservations();
              roomsTable.refresh();
            });
          }
        });*/
      }
  }

  // ---------------- BUTTONS ----------------

  @FXML
  private void onCheckIn() {
    viewModel.checkIn(roomsTable.getSelectionModel().getSelectedItem());
    roomsTable.refresh();
  }

  @FXML
  private void onCheckOut() {
    viewModel.checkOut(roomsTable.getSelectionModel().getSelectedItem());
    roomsTable.refresh();
  }

  @FXML
  private void onCancel() {

    Reservation selected =
        roomsTable.getSelectionModel().getSelectedItem();

    if (selected == null) {
      return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    alert.setTitle("Cancel Reservation");
    alert.setHeaderText("Confirm Cancellation");
    alert.setContentText(
        "Do you really want to cancel reservation #" +
            selected.getReservationNumber() + " ?"
    );

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {

      viewModel.cancel(selected);

      roomsTable.refresh();
    }
  }

  @FXML
  private void onSearch() {
    viewModel.search(
        reservationNumberTextField.getText(),
        roomNumberTextField.getText(),
        emailTextField.getText()
    );
  }

  @FXML
  private void onLogOut() {
    viewModel.logout();
  }
}
