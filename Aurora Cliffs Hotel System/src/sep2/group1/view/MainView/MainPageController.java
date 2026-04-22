package sep2.group1.view.MainView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.MainPageViewModel;

import java.sql.SQLXML;

public class MainPageController
{
  private ViewHandler viewHandler;
  private MainPageViewModel viewModel;

  public void init(ViewHandler viewHandler, MainPageViewModel viewModel)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
  }

  @FXML
  private void onGuest(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource(
              "/sep2/group1/view/RoomDetailsView/RoomDetails.fxml")
      );

      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

      ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void onManager(ActionEvent event)
  {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource(
              "/sep2/group1/view/LoginView/LoginView.fxml")
      );

      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

      ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
