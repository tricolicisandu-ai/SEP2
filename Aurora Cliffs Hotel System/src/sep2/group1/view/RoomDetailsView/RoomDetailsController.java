package sep2.group1.view.RoomDetailsView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.AvailableState;
import sep2.group1.model.Room;
import sep2.group1.view.ReservationView.ReservationController;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.RoomDetailsViewModel;

public class RoomDetailsController {

  // DatePickers
  @FXML private DatePicker checkInPicker;
  @FXML private DatePicker checkOutPicker;

  // ChoiceBox
  @FXML private ChoiceBox<Integer> numberOfGuestsPicker;

  // Buttons
  @FXML private Button resetButton;
  @FXML private Button viewRoomsButton;
  @FXML private Button selectButton;

  // Table
  @FXML private TableView<Room> roomsTable;

  @FXML private TableColumn<Room, Integer> colIndex;
  @FXML private TableColumn<Room, Integer> colRoomNumber;
  @FXML private TableColumn<Room, String> colRoomType;
  @FXML private TableColumn<Room, Integer> colBeds;
  @FXML private TableColumn<Room, Integer> colGuests;
  @FXML private TableColumn<Room, Double> colPrice;

  private ObservableList<Room> allRooms = FXCollections.observableArrayList();

  @FXML
  public void  initialize() {

    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> {
      roomsTable.refresh();
    });

    checkOutPicker.valueProperty().addListener((obs, oldV, newV) -> {
      roomsTable.refresh();
    });

    checkInPicker.setOnAction(e -> roomsTable.refresh());
    checkOutPicker.setOnAction(e -> roomsTable.refresh());

    checkInPicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);

        if (empty) return;

        if (date.isBefore(LocalDate.now())) {
          setDisable(true);
          setStyle("-fx-background-color: #eeeeee;");
        }
      }
    });

    checkOutPicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);

        if (empty) return;

        if (checkInPicker.getValue() != null) {
          if (!date.isAfter(checkInPicker.getValue())) {
            setDisable(true);
            setStyle("-fx-background-color: #eeeeee;");
          }
        } else {
          if (date.isBefore(LocalDate.now())) {
            setDisable(true);
          }
        }
      }
    });

    colRoomNumber.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getRoomNumber()).asObject());

    colRoomType.setCellValueFactory(data ->
        new SimpleStringProperty(data.getValue().getRoomType()));

    colBeds.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getNumberOfBeds()).asObject());

    colGuests.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getNumberOfGuest()).asObject());

    colIndex.setCellFactory(col -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setText(null);
        } else {
          Room currentRoom = getTableRow().getItem();
          int originalIndex = allRooms.indexOf(currentRoom) + 1;
          setText(String.valueOf(originalIndex));
        }
      }
    });

    colPrice.setCellFactory(column -> new TableCell<Room, Double>() {

      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setText(null);
          return;
        }

        Room room = getTableRow().getItem();

        var checkIn = checkInPicker.getValue();
        var checkOut = checkOutPicker.getValue();

        long nights = 1;

        if (checkIn != null && checkOut != null) {
          nights = ChronoUnit.DAYS.between(checkIn, checkOut);

          if (nights <= 0) {
            nights = 1;
          }
        }

        double total = room.getPrice() * nights;

        setText(total + " €");
      }
    });

    numberOfGuestsPicker.getItems().addAll(1, 2, 3, 4);

    allRooms.add(new Room(101, "Single", 1, 2, 1));
    allRooms.add(new Room(102, "Single", 1, 2, 1));

    allRooms.add(new Room(103, "Double", 2, 3, 2));
    allRooms.add(new Room(104, "Double", 2, 3, 2));
    allRooms.add(new Room(105, "Double", 2, 3, 2));

    allRooms.add(new Room(106, "Twin", 2, 2, 2));
    allRooms.add(new Room(107, "Twin", 2, 2, 2));

    allRooms.add(new Room(108, "Suite", 4, 4, 4));
    allRooms.add(new Room(109, "Suite", 4, 4, 4));
    allRooms.add(new Room(110, "Suite", 4, 5, 4));

    allRooms.add(new Room(111, "Family", 3, 4, 3));
    allRooms.add(new Room(112, "Family", 3, 5, 3));

    allRooms.add(new Room(113, "Deluxe", 2, 2, 2));
    allRooms.add(new Room(114, "Deluxe", 2, 3, 2));
  }

  @FXML
  private void onReset() {
    checkInPicker.setValue(null);
    checkOutPicker.setValue(null);
    numberOfGuestsPicker.setValue(null);
    roomsTable.setItems(FXCollections.observableArrayList());
  }

  @FXML
  private void onViewRooms() {

    Integer guests = numberOfGuestsPicker.getValue();
    var checkIn = checkInPicker.getValue();
    var checkOut = checkOutPicker.getValue();

    roomsTable.setItems(allRooms.filtered(room -> {

      if (guests != null && room.getNumberOfBeds() < guests) {
        return false;
      }

      if (!(room.getState() instanceof AvailableState)) {
        return false;
      }

      if (checkIn != null && checkOut != null) {

        if (room.getCheckInDate() != null && room.getCheckOutDate() != null) {

          boolean overlap =
              !checkOut.isBefore(room.getCheckInDate()) &&
                  !checkIn.isAfter(room.getCheckOutDate());

          if (overlap) {
            return false;
          }
        }
      }

      return true;
    }));
  }

  @FXML
  private void onSelect() {

    Room selected = roomsTable.getSelectionModel().getSelectedItem();

    if (selected == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No selection");
      alert.setContentText("Please select a room first!");
      alert.showAndWait();
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource(
              "/sep2/group1/view/ReservationView/Reservation.fxml"
          )
      );

      Parent root = loader.load();

      ReservationController controller = loader.getController();
      controller.setRoom(selected);
      controller.setDates(checkInPicker.getValue(), checkOutPicker.getValue());

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle("Reservation");
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init(ViewHandler viewHandler, RoomDetailsViewModel roomDetailsViewModel) {
  }


  @FXML
  private void onOpenManager() {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/sep2/group1/view/ManagerView/ManagerView.fxml")
      );

      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Manager View");
      stage.setScene(new Scene(root));
      stage.show();

      // zavrie aktuálne okno (RoomDetails)
      Stage current = (Stage) viewRoomsButton.getScene().getWindow();
      current.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}