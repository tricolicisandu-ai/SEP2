package sep2.group1.view.ManagerView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.model.Room;

import java.text.DateFormat;

public class ManagerController
{
  // TextFields
  @FXML private TextField reservationNumberTextField;
  @FXML private TextField roomNumberTextField;
  @FXML private TextField emailTextField;

  // Buttons
  @FXML private Button searchButton;
  @FXML private Button cancelButton;
  @FXML private Button checkInButton;
  @FXML private Button checkOutButton;

  // Table
  @FXML private TableView<Room> roomsTable;

  @FXML private TableColumn<Room, Integer> colIndex;
  @FXML private TableColumn<Room, Integer> colReservationNumber;
  @FXML private TableColumn<Room, Integer> colRoomNumber;
  @FXML private TableColumn<Room, String> colEmail;
  @FXML private TableColumn<Room, DateFormat> colCheckIn;
  @FXML private TableColumn<Room, DateFormat> colCheckOut;
  @FXML private TableColumn<Room, String> colStatus;

  private ObservableList<Room> allRooms = FXCollections.observableArrayList();

  @FXML
  public void  initialize()
  {

  }
}
