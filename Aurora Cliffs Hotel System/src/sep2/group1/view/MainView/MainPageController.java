package sep2.group1.view.MainView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.MainPageViewModel;

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
  private void onGuest() {
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

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @FXML
  private void onManager()
  {
    System.out.println("Manager clicked (not implemented yet)");
  }
}
