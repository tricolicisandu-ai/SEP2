package sep2.group1.view.ManagerView;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.model.Reservation;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ManagerViewModel;

public class ManagerController {

  @FXML private TableView<Reservation> roomsTable;

  @FXML private TableColumn<Reservation, Integer> colReservationNumber;
  @FXML private TableColumn<Reservation, Integer> colRoomNumber;
  @FXML private TableColumn<Reservation, String> colEmail;
  @FXML private TableColumn<Reservation, String> colStatus;

  @FXML private TextField reservationNumberTextField;
  @FXML private TextField roomNumberTextField;
  @FXML private TextField emailTextField;

  private ManagerViewModel viewModel;

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

    reservationNumberTextField.textProperty().addListener((obs, old, newVal) -> onSearch());
    roomNumberTextField.textProperty().addListener((obs, old, newVal) -> onSearch());
    emailTextField.textProperty().addListener((obs, old, newVal) -> onSearch());
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
    viewModel.cancel(roomsTable.getSelectionModel().getSelectedItem());
    roomsTable.refresh();
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
/*OLD CODE DONT TOUCH*/
/*package sep2.group1.view.ManagerView;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sep2.group1.model.Reservation;
import sep2.group1.model.ReservationManager;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ManagerViewModel;

import java.time.LocalDate;

public class ManagerController {

  @FXML private TableView<Reservation> roomsTable;

  // Text Fields
  @FXML private TextField reservationNumberTextField;
  @FXML private TextField roomNumberTextField;
  @FXML private TextField emailTextField;

  // Table Columns
  @FXML private TableColumn<Reservation, Integer> colIndex;
  @FXML private TableColumn<Reservation, Integer> colReservationNumber;
  @FXML private TableColumn<Reservation, Integer> colRoomNumber;
  @FXML private TableColumn<Reservation, String> colEmail;
  @FXML private TableColumn<Reservation, LocalDate> colCheckIn;
  @FXML private TableColumn<Reservation, LocalDate> colCheckOut;
  @FXML private TableColumn<Reservation, String> colStatus;

  // Buttons
  @FXML private Button logOutButton;
  @FXML private Button searchButton;
  @FXML private Button cancelReservation;
  @FXML private Button checkInButton;
  @FXML private Button checkOutButton;


  private ObservableList<Reservation> reservations = FXCollections.observableArrayList();
  private FilteredList<Reservation> filteredRooms;

  @FXML
  public void initialize()
  {

    // Loading all reservations
    ObservableList<Reservation> allReservations = ReservationManager.getReservations();

    this.reservations = allReservations;

    // Creating all filtered rooms
    filteredRooms = new FilteredList<>(allReservations, p -> true);

    // Adding filtered rooms to the table
    roomsTable.setItems(filteredRooms);

    System.out.println("Reservations loaded size: " + reservations.size());

    roomsTable.refresh();



    reservations.addListener((javafx.collections.ListChangeListener<Reservation>) c -> {roomsTable.refresh();
    });

    for (Reservation r : reservations) {
      System.out.println(
          "RES: " + r.getReservationNumber()
              + " | checkIn=" + r.getCheckIn()
              + " | checkOut=" + r.getCheckOut()
      );
    }

    colReservationNumber.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getReservationNumber()).asObject());

    colRoomNumber.setCellValueFactory(data ->
        new SimpleIntegerProperty(data.getValue().getRoomNumber()).asObject());

    colEmail.setCellValueFactory(data ->
        new SimpleStringProperty(data.getValue().getEmail()));

    colCheckIn.setCellValueFactory(data ->
        new SimpleObjectProperty<>(data.getValue().getCheckIn()));

    colCheckOut.setCellValueFactory(data ->
        new SimpleObjectProperty<>(data.getValue().getCheckOut()));

    colCheckIn.setCellFactory(col -> new TableCell<>() {
      @Override
      protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setText(null);
        } else {
          setText(item.toString());
        }
      }
    });

    colCheckOut.setCellFactory(col -> new TableCell<>() {
      @Override
      protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setText(null);
        } else {
          setText(item.toString());
        }
      }
    });

    colStatus.setCellValueFactory(data ->
        new SimpleStringProperty(data.getValue().getStatus()));

    colIndex.setCellFactory(col -> new TableCell<>() {

      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow().getItem() == null) {
          setText(null);
        } else {
          Reservation currentRes = getTableRow().getItem();
          int originalIndex = reservations.indexOf(currentRes) + 1;
          setText(String.valueOf(originalIndex));
        }
      }
    });
  }

  // --- BUTTON LOGIC ---

  @FXML
  private void onCheckIn() {
    Reservation selected = roomsTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      selected.setStatus("Occupied");
      roomsTable.refresh();
    }
  }

  @FXML
  private void onCheckOut() {
    Reservation selected = roomsTable.getSelectionModel().getSelectedItem();

    if (selected != null) {
      selected.setStatus("In Maintenance");
      roomsTable.refresh();

      Thread thread = new Thread(() -> {
        try {
          // Sets the timer for 3 seconds to simulate the maintenance process
          Thread.sleep(3000);
          Platform.runLater(() -> {
            // Removes the reservation from the list
            reservations.remove(selected);

            System.out.println("Maintenance finished for room: " + selected.getRoomNumber());
            roomsTable.refresh();
          });

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });

      thread.setDaemon(true);
      thread.start();
    }
  }

  @FXML
  private void onCancel() {
    Reservation selected = roomsTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      selected.setStatus("Available");
      reservations.remove(selected);
    }
  }

  public void onLogOut(javafx.event.ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/sep2/group1/view/MainView/MainPage.fxml")
      );

      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

      ((Stage)((Button)event.getSource()).getScene().getWindow()).close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onSearch() {
    String resNoFilter = reservationNumberTextField.getText();
    String roomNoFilter = roomNumberTextField.getText();
    String emailFilter = emailTextField.getText().toLowerCase();

    filteredRooms.setPredicate(reservation -> {
      // If all text fields are empty, return all reservation list.
      if (resNoFilter.isEmpty() && roomNoFilter.isEmpty() && emailFilter.isEmpty()) {
        return true;
      }

      // Check Reservation Number
      boolean isResNo = true;
      if (!resNoFilter.isEmpty()) {
        isResNo = String.valueOf(reservation.getReservationNumber()).contains(resNoFilter);
      }

      // Check Room Number
      boolean isRoomNo = true;
      if (!roomNoFilter.isEmpty()) {
        isRoomNo = String.valueOf(reservation.getRoomNumber()).contains(roomNoFilter);
      }

      // Check Email
      boolean isEmail = true;
      if (!emailFilter.isEmpty()) {
        isEmail = reservation.getEmail().toLowerCase().contains(emailFilter);
      }

      // Reservation filter return
      return isResNo && isRoomNo && isEmail;
    });

    roomsTable.refresh();
  }

  public void init(ViewHandler viewHandler, ManagerViewModel managerViewModel)
  {
  }
}*/