package sep2.group1.view.RoomDetailsView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.model.Room;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.RoomDetailsViewModel;

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

  @FXML
  public void initialize() {
    // Listeners for refreshing changes (old prices
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> roomsTable.refresh());
    checkOutPicker.valueProperty().addListener((obs, oldV, newV) -> roomsTable.refresh());

    // Základná validácia Check-in pred Check-out
    checkOutPicker.setOnMouseClicked(e -> {
      if (checkInPicker.getValue() == null) {
        showAlert("Select Check-In First", "Please select check-in date first!");
        checkOutPicker.hide();
      }
    });

    // Reset Check-out ak sa zmení Check-in
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> checkOutPicker.setValue(null));

    // --- DAY CELL FACTORIES (Obmedzenia v kalendári) ---
    setupDatePickerConstraints();

    // --- TABLE COLUMNS SETUP ---
    setupTableColumns();

    numberOfGuestsPicker.getItems().addAll(1, 2, 3, 4);
  }

  public void init(ViewHandler viewHandler, RoomDetailsViewModel roomDetailsViewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = roomDetailsViewModel;
    this.allRooms = viewModel.getAllRooms();

    // Na začiatku zobrazíme všetky izby
    refreshTable();
  }

  // Táto metóda zabezpečí, že sa tabuľka po rezervácii aktualizuje
  public void refreshTable() {
    if (viewModel != null) {
      roomsTable.setItems(viewModel.getAllRooms());
      roomsTable.refresh();
    }
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

    // 1. NAJSKÔR uložím dáta do ViewModelu
    viewModel.setReservationData(
        selected,
        checkIn,
        checkOut,
        numberOfGuestsPicker.getValue() != null ? numberOfGuestsPicker.getValue() : selected.getNumberOfGuest()
    );

    // 2. AŽ POTOM otvorím okno (aby si Controller mohol tie dáta vytiahnuť)
    viewModel.openReservationView("reservation");
  }

  @FXML
  private void onLogOut() {
    viewHandler.openView("main");
  }

  // --- POMOCNÉ METÓDY (pre čistotu kódu) ---

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
/*OLD CODE DONT TOUCH*/
/*package sep2.group1.view.RoomDetailsView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.AvailableState;
import sep2.group1.model.Room;
import sep2.group1.model.RoomManager;
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

  private ObservableList<Room> allRooms = RoomManager.getRooms();

  @FXML
  public void initialize() {

    // Refresh table when dates change
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> {
      roomsTable.refresh();
    });

    checkOutPicker.valueProperty().addListener((obs, oldV, newV) -> {
      roomsTable.refresh();
      // 🔥 REFRESH Check-In cells when Check-Out date is picked
      checkInPicker.setDayCellFactory(checkInPicker.getDayCellFactory());
    });

    checkOutPicker.setOnMouseClicked(e -> {
      if (checkInPicker.getValue() == null) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Select Check-In First");
        alert.setHeaderText(null);
        alert.setContentText("Please select check-in date first!");
        alert.showAndWait();
        checkOutPicker.hide();
      }
    });

    // Reset check-out if check-in changes to avoid logical errors
    checkInPicker.valueProperty().addListener((obs, oldV, newV) -> {
      checkOutPicker.setValue(null);
      roomsTable.refresh();
    });

    // --- Check-in / Check-out (Restrictions) ---

    checkInPicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (empty) return;

        // Cannot select past date
        if (date.isBefore(LocalDate.now())) {
          setDisable(true);
          setStyle("-fx-background-color: #eeeeee;");
        }
        // Cannot select check-in date AFTER check-out (if check-out is already set)
        else if (checkOutPicker.getValue() != null && !date.isBefore(checkOutPicker.getValue())) {
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
          // Cannot select Check-Out date before or on the same day as Check-In
          if (!date.isAfter(checkInPicker.getValue())) {
            setDisable(true);
            setStyle("-fx-background-color: #eeeeee;");
          }
        } else {
          // If no check-in, just disable past
          if (date.isBefore(LocalDate.now())) {
            setDisable(true);
            setStyle("-fx-background-color: #eeeeee;");
          }
        }
      }
    });

    // --- TABLE CONFIGURATION ---

    colRoomNumber.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getRoomNumber()).asObject());

    colRoomType.setCellValueFactory(data ->
        new SimpleStringProperty(data.getValue().getRoomType()));

    colBeds.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getNumberOfBeds()).asObject());

    colGuests.setCellValueFactory(data -> {
      Integer selectedGuests = numberOfGuestsPicker.getValue();
      int valueToShow = (selectedGuests != null) ?
          selectedGuests : data.getValue().getNumberOfGuest();
      return new SimpleIntegerProperty(valueToShow).asObject();
    });

    colIndex.setCellFactory(col -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setText(null);
        } else {
          setText(String.valueOf(getIndex() + 1));
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
          if (nights <= 0) nights = 1;
        }

        double total = room.getPrice() * nights;
        setText(total + " €");
      }
    });

    numberOfGuestsPicker.getItems().addAll(1, 2, 3, 4);
  }

  public void refreshTable() {
    roomsTable.setItems(allRooms.filtered(room ->
        room.getState() instanceof AvailableState
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
    Integer guests = numberOfGuestsPicker.getValue();
    var checkIn = checkInPicker.getValue();
    var checkOut = checkOutPicker.getValue();

    roomsTable.setItems(allRooms.filtered(room -> {
      if (guests != null && guests > room.getNumberOfBeds()) return false;
      if (!(room.getState() instanceof AvailableState)) return false;

      if (checkIn != null && checkOut != null) {
        if (room.getCheckInDate() != null && room.getCheckOutDate() != null) {
          boolean overlap = !checkOut.isBefore(room.getCheckInDate()) &&
              !checkIn.isAfter(room.getCheckOutDate());
          if (overlap) return false;
        }
      }
      return true;
    }));
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

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/sep2/group1/view/ReservationView/Reservation.fxml"));
      Parent root = loader.load();

      ReservationController controller = loader.getController();
      controller.setParentController(this);
      controller.setRoom(selected);
      controller.setDates(checkIn, checkOut);

      Integer guests = numberOfGuestsPicker.getValue();
      controller.setNumberOfGuests(guests != null ? guests : selected.getNumberOfGuest());

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle("Reservation");
      stage.setResizable(false);
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error", "Could not open the reservation window.");
    }
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public void init(ViewHandler viewHandler, RoomDetailsViewModel roomDetailsViewModel) {}

  @FXML
  private void onLogOut() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/sep2/group1/view/MainView/MainPage.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

      Stage current = (Stage) viewRoomsButton.getScene().getWindow();
      current.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}*/