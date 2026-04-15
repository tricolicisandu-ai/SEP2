package sep2.group1.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sep2.group1.view.MainView.MainPageController;
import sep2.group1.view.ReservationView.ReservationController;
import sep2.group1.view.RoomDetailsView.RoomDetailsController;
import sep2.group1.viewmodel.ViewModelFactory;

public class ViewHandler
{
  private Stage stage;
  private Scene scene;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
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
      FXMLLoader loader;
      Parent root;

      if (id.equals("main"))
      {
        loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        root = loader.load();

        MainPageController controller = loader.getController();
        controller.init(this, viewModelFactory.getMainPageViewModel());
      }
      else if (id.equals("roomDetails"))
      {
        loader = new FXMLLoader(getClass().getResource(
            "RoomDetailsView/RoomDetails.fxml"));
        root = loader.load();

        RoomDetailsController controller = loader.getController();
        controller.init(this, viewModelFactory.getRoomDetailsViewModel());
      }
      else
      {
        loader = new FXMLLoader(getClass().getResource(
            "ReservationView/Reservation.fxml"));
        root = loader.load();

        ReservationController controller = loader.getController();
        controller.init(this, viewModelFactory.getReservationViewModel());
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
}
