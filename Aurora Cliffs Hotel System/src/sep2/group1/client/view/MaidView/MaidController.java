package sep2.group1.client.view.MaidView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.MaidViewModel;
import sep2.group1.server.model.Reservation;

public class MaidController {

  @FXML
  private TableView<Reservation> roomsTable;

  @FXML
  private TableColumn<Reservation, Integer> colRoomNumber;

  @FXML
  private TableColumn<Reservation, String> colEmail;

  @FXML
  private TableColumn<Reservation, String> colStatus;

  private MaidViewModel viewModel;
  private ViewHandler viewHandler;

  public void init(
      ViewHandler viewHandler,
      MaidViewModel viewModel) {

    this.viewHandler = viewHandler;
    this.viewModel = viewModel;

    roomsTable.setItems(
        viewModel.getReservations()
    );

    colRoomNumber.setCellValueFactory(
        new PropertyValueFactory<>("roomNumber")
    );

    colEmail.setCellValueFactory(
        new PropertyValueFactory<>("email")
    );

    colStatus.setCellValueFactory(
        new PropertyValueFactory<>("status")
    );
  }

  @FXML
  private void onClean() {

    Reservation selected =
        roomsTable.getSelectionModel()
            .getSelectedItem();

    if (selected != null) {

      viewModel.cleanRoom(selected);

      Alert alert =
          new Alert(Alert.AlertType.INFORMATION);

      alert.setTitle("Done Cleaning");

      alert.setHeaderText(null);

      alert.setContentText(
          "Room #"
              + selected.getRoomNumber()
              + " has been cleaned."
      );

      alert.showAndWait();

      roomsTable.refresh();
    }
  }

  @FXML
  private void onLogOut() {
    viewModel.logout();
  }
}