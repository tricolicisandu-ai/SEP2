package sep2.group1.client.view.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.MainPageViewModel;

public class MainPageController {

  private ViewHandler viewHandler;
  private MainPageViewModel viewModel;

  @FXML private ImageView auroraImage;
  @FXML private Button guestButton;
  @FXML private Button managerButton;

  public void init(ViewHandler viewHandler, MainPageViewModel viewModel) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
  }

  @FXML
  private void onGuest() {
    viewHandler.openView("roomDetails");
  }

  @FXML
  private void onManager() {
    viewHandler.openView("login");
  }

  @FXML
  private void onMaid() {
    viewHandler.openView("maidLogin");
  }
}