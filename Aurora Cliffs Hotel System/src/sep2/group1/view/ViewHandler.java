package sep2.group1.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sep2.group1.view.LoginView.LoginViewController;
import sep2.group1.view.MainView.MainPageController;
import sep2.group1.view.ManagerView.ManagerController;
import sep2.group1.view.ReservationView.ReservationController;
import sep2.group1.view.RoomDetailsView.RoomDetailsController;
import sep2.group1.viewmodel.RoomDetailsViewModel;
import sep2.group1.viewmodel.ViewModelFactory;

public class ViewHandler
{

  private Stage stage;
  private ViewModelFactory viewModelFactory;
  private RoomDetailsController roomDetailsController;

  public void setFactory(ViewModelFactory factory)
  {
    this.viewModelFactory = factory;
  }

  public void start(Stage primaryStage)
  {
    this.stage = primaryStage;
    openView("main");
  }

  public void openView(String id)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader();
      Parent root;

      switch (id)
      {
        case "main":
          loader.setLocation(getClass().getResource("MainView/MainPage.fxml"));
          root = loader.load();
          MainPageController mainController = loader.getController();
          mainController.init(this, viewModelFactory.getMainPageViewModel());
          break;

        case "roomDetails":
          loader.setLocation(
              getClass().getResource("RoomDetailsView/RoomDetails.fxml"));
          root = loader.load();
          this.roomDetailsController = loader.getController();
          roomDetailsController.init(this,
              viewModelFactory.getRoomDetailsViewModel());
          break;

        case "manager":
          loader.setLocation(
              getClass().getResource("ManagerView/ManagerView.fxml"));
          root = loader.load();
          ManagerController managerController = loader.getController();
          managerController.init(this, viewModelFactory.getManagerViewModel());
          break;

        case "login":
          loader.setLocation(
              getClass().getResource("LoginView/LoginView.fxml"));
          root = loader.load();
          LoginViewController loginController = loader.getController();
          loginController.init(this, viewModelFactory.getLoginViewModel());
          break;

        default:
          throw new IllegalArgumentException("Unknown view: " + id);
      }

      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Aurora Cliffs Hotel System");
      stage.show();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void openReservationView(String id)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(
          getClass().getResource("ReservationView/Reservation.fxml"));
      Parent root = loader.load();

      ReservationController reservationController = loader.getController();
      reservationController.init(this,
          viewModelFactory.getReservationViewModel());

      RoomDetailsViewModel detailsVM = viewModelFactory.getRoomDetailsViewModel();

      reservationController.setRoom(detailsVM.getSelectedRoom());
      reservationController.setDates(detailsVM.getCheckIn(),
          detailsVM.getCheckOut());
      reservationController.setNumberOfGuests(detailsVM.getNumberOfGuests());

      reservationController.setParentController(roomDetailsController);

      Stage newStage = new Stage();
      newStage.setScene(new Scene(root));
      newStage.setTitle("Reservation");
      newStage.setResizable(false);
      newStage.show();

    }
    catch (Exception e)
    {
      e.printStackTrace();
      showAlert("Error", "Could not open the reservation window.");
    }
  }

  private void showAlert(String title, String content)
  {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}