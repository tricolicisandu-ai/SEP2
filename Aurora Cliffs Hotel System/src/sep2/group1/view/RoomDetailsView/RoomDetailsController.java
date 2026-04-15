package sep2.group1.view.RoomDetailsView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.Room;
import sep2.group1.view.ReservationView.ReservationController;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.RoomDetailsViewModel;

public class RoomDetailsController {

  //  DatePickers
  @FXML private DatePicker checkInPicker;
  @FXML private DatePicker checkOutPicker;

  //  ChoiceBox
  @FXML private ChoiceBox<Integer> numberOfGuestsPicker;

  // Buttons
  @FXML private Button resetButton;
  @FXML private Button viewRoomsButton;
  @FXML private Button selectButton;

  // Table
  @FXML private TableView<Room> roomsTable;

  @FXML private TableColumn<Room, Integer> colNumber;
  @FXML private TableColumn<Room, Integer> colRoomNumber;
  @FXML private TableColumn<Room, String> colRoomType;
  @FXML private TableColumn<Room, Integer> colBeds;
  @FXML private TableColumn<Room, Integer> colGuests;
  @FXML private TableColumn<Room, Double> colPrice;

  //  INIT (spustí sa automaticky)
  @FXML
  public void initialize() {

    // naplnenie ChoiceBox
    numberOfGuestsPicker.getItems().addAll(1, 2, 3, 4);

    // Table columns (napojenie na Room)
    colRoomNumber.setCellValueFactory(data ->
        new javafx.beans.property.SimpleIntegerProperty(data.getValue().getRoomNumber()).asObject()
    );

    colRoomType.setCellValueFactory(data ->
        new javafx.beans.property.SimpleStringProperty(data.getValue().getRoomType())
    );

    colBeds.setCellValueFactory(data ->
        new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumberOfBeds()).asObject()
    );

    colGuests.setCellValueFactory(data ->
        new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumberOfGuest()).asObject()
    );

    colPrice.setCellValueFactory(data ->
        new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject()
    );
  }

  //  Reset button
  @FXML
  private void onReset() {
    checkInPicker.setValue(null);
    checkOutPicker.setValue(null);
    numberOfGuestsPicker.setValue(null);
    roomsTable.getItems().clear();
  }

  //  View Rooms button
  @FXML
  private void onView() {
    // test data
    roomsTable.getItems().clear();

    roomsTable.getItems().add(new Room(101, "Double Bed",2, 120, 2));
    roomsTable.getItems().add(new Room(102, "Twin Bed + Single Bed",3, 150, 3));
    roomsTable.getItems().add(new Room(103, "Single Bed",1, 90, 1));
  }

  //  Select button
  @FXML
  private void onSelect() {

    Room selected = roomsTable.getSelectionModel().getSelectedItem();

    //  nič nie je vybrané
    if (selected == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No selection");
      alert.setHeaderText(null);
      alert.setContentText("Please select a room first!");
      alert.showAndWait();
      return;
    }

    // ✔ otvorenie reservation okna
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource(
              "/sep2/group1/view/ReservationView/Reservation.fxml")
      );

      Parent root = loader.load();

      // poslanie izby do druhého okna
      ReservationController controller = loader.getController();
      controller.setRoom(selected);

      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public void init(ViewHandler viewHandler, RoomDetailsViewModel roomDetailsViewModel)
  {
  }
}
