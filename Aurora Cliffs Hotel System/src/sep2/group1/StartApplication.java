package sep2.group1;

import javafx.application.Application;
import javafx.stage.Stage;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.ViewModelFactory;
import sep2.group1.server.model.ReservationManager;
import sep2.group1.server.persistence.ReservationDAO;

public class StartApplication extends Application
{
 /* @Override
  public void start(Stage primaryStage) {

    ViewHandler viewHandler = new ViewHandler();
    ViewModelFactory factory = new ViewModelFactory(viewHandler);

    viewHandler.setFactory(factory);
    viewHandler.start(primaryStage);
    ReservationManager.loadFromDB(
        new ReservationDAO().getAllReservations()
    );
  }*/

  @Override
  public void start(Stage primaryStage) {

    try {
      // TEST SERVER/DB PRIPOJENIA
      new ReservationDAO().getAllReservations();
    } catch (Exception e) {

      javafx.scene.control.Alert alert =
          new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

      alert.setTitle("Server unavailable");
      alert.setHeaderText("Cannot connect to server");
      alert.setContentText("Please start the server first.");

      alert.showAndWait();

      javafx.application.Platform.exit();
      return;
    }

    ViewHandler viewHandler = new ViewHandler();
    ViewModelFactory factory = new ViewModelFactory(viewHandler);

    viewHandler.setFactory(factory);
    viewHandler.start(primaryStage);

    ReservationManager.loadFromDB(
        new ReservationDAO().getAllReservations()
    );
  }


}
