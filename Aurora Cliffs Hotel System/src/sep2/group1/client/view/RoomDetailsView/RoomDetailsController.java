package sep2.group1.client.view.RoomDetailsView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.client.Client;
import sep2.group1.server.model.ReservationManager;
import sep2.group1.server.model.Room;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.RoomDetailsViewModel;

public class RoomDetailsController {

  @FXML private DatePicker checkInPicker;
  @FXML private DatePicker checkOutPicker;
  @FXML private ChoiceBox<Integer> numberOfGuestsPicker;
  @FXML private TableView<Room> roomsTable;

  @FXML private TableColumn<Room, Integer> colIndex;
  @FXML private TableColumn<Room, Integer> colRoomNumber;
  @FXML private TableColumn<Room, String> colRoomType;
  @FXML private TableColumn<Room, Integer> colBeds;
  @FXML private TableColumn<Room, Integer> colGuests;
  @FXML private TableColumn<Room, Double> colPrice;

  private ViewHandler viewHandler;
  private RoomDetailsViewModel viewModel;
  private ObservableList<Room> allRooms;
  private boolean listenerAdded = false;

  @FXML
  public void initialize() {
    // Listeners for refreshing changes (old prices
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> roomsTable.refresh());
    checkOutPicker.valueProperty().addListener((obs, oldV, newV) -> roomsTable.refresh());

    checkOutPicker.setOnMouseClicked(e -> {
      if (checkInPicker.getValue() == null) {
        showAlert("Select Check-In First", "Please select check-in date first!");
        checkOutPicker.hide();
      }
    });

    // Reset Check-out
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> checkOutPicker.setValue(null));

    // --- DAY CELL FACTORIES ---
    setupDatePickerConstraints();

    // --- TABLE COLUMNS SETUP ---
    setupTableColumns();

    numberOfGuestsPicker.getItems().addAll(1, 2, 3, 4);
  }

  public void init(ViewHandler viewHandler, RoomDetailsViewModel roomDetailsViewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = roomDetailsViewModel;
    this.allRooms = viewModel.getAllRooms();

    refreshTable();

    /*viewModel.getClient().addEventHandler(msg -> {

      if (msg.equals("RESERVATION_CHANGED")) {

        refreshTable();
      }
    });*/
    if (!listenerAdded) {

      listenerAdded = true;

      Client.getInstance().addEventHandler(msg -> {

        if (msg.startsWith("ROOM_RESERVED")) {

          String[] p = msg.split(",");

          int roomNumber =
              Integer.parseInt(p[1]);

          Alert alert =
              new Alert(Alert.AlertType.INFORMATION);

          alert.setTitle("Room Reserved");

          alert.setHeaderText(null);

          alert.setContentText(
              "Room " + roomNumber +
                  " was reserved by another guest."
          );

          alert.show();

          refreshTable();
        }
      });
    }
  }


  /*public void refreshTable() {
    if (viewModel != null) {
      ReservationManager.refreshReservations();
      roomsTable.setItems(viewModel.getAllRooms());
      roomsTable.refresh();
    }
  }*/

  public void refreshTable() {

    roomsTable.setItems(viewModel.getFilteredRooms(
        numberOfGuestsPicker.getValue(),
        checkInPicker.getValue(),
        checkOutPicker.getValue()
    ));

    roomsTable.refresh();
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
    roomsTable.setItems(viewModel.getFilteredRooms(
        numberOfGuestsPicker.getValue(),
        checkInPicker.getValue(),
        checkOutPicker.getValue()
    ));
    roomsTable.refresh();
  }

  @FXML
  private void onSelect() {
    Room selected = roomsTable.getSelectionModel().getSelectedItem();
    LocalDate checkIn = checkInPicker.getValue();
    LocalDate checkOut = checkOutPicker.getValue();

    if (selected == null) {
      showAlert("No room selection", "Please select a room from the table first!");
      return;
    }

    if (checkIn == null || checkOut == null) {
      showAlert("Missing Dates", "Please select check-in and check-out dates first!");
      return;
    }


    viewModel.setReservationData(
        selected,
        checkIn,
        checkOut,
        numberOfGuestsPicker.getValue() != null ? numberOfGuestsPicker.getValue() : selected.getNumberOfGuest()
    );

    viewModel.openReservationView("reservation");
  }

  @FXML
  private void onLogOut() {
    viewHandler.openView("main");
  }


  private void setupTableColumns() {
    colRoomNumber.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getRoomNumber()).asObject());
    colRoomType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoomType()));
    colBeds.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNumberOfBeds()).asObject());

    colGuests.setCellValueFactory(data -> {
      Integer selectedGuests = numberOfGuestsPicker.getValue();
      return new SimpleIntegerProperty(selectedGuests != null ? selectedGuests : data.getValue().getNumberOfGuest()).asObject();
    });

    colIndex.setCellFactory(col -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText((empty || getTableRow() == null || getTableRow().getItem() == null) ? null : String.valueOf(getIndex() + 1));
      }
    });

    colPrice.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setText(null);
        } else {
          Room room = getTableRow().getItem();
          long nights = (checkInPicker.getValue() != null && checkOutPicker.getValue() != null)
              ? Math.max(1, ChronoUnit.DAYS.between(checkInPicker.getValue(), checkOutPicker.getValue()))
              : 1;
          setText((room.getPrice() * nights) + " €");
        }
      }
    });
  }

  private void setupDatePickerConstraints() {
    checkInPicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (!empty && date.isBefore(LocalDate.now())) {
          setDisable(true);
          setStyle("-fx-background-color: #eeeeee;");
        }
      }
    });

    checkOutPicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (!empty) {
          if (checkInPicker.getValue() != null) {
            if (!date.isAfter(checkInPicker.getValue())) {
              setDisable(true);
              setStyle("-fx-background-color: #eeeeee;");
            }
          } else if (date.isBefore(LocalDate.now())) {
            setDisable(true);
          }
        }
      }
    });
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
