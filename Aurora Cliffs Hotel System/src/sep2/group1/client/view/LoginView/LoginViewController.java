package sep2.group1.client.view.LoginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.LoginViewModel;

public class LoginViewController {

  @FXML private TextField usernameTextField;
  @FXML private PasswordField passwordPasswordField;

  private LoginViewModel viewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, LoginViewModel viewModel) {
    this.viewModel = viewModel;
    this.viewHandler = viewHandler;
  }

  @FXML
  private void onLogin(ActionEvent event) {

    String username = usernameTextField.getText();
    String password = passwordPasswordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
      showAlert("You must fill username and password.");
      return;
    }

    boolean success = viewModel.login(username, password);

    if (success) {
      viewModel.openManagerView("manager");

    } else {
      showAlert("Invalid username or password.");
    }
  }

  @FXML
  private void onForgotLogin() {
    showAlert("Username: manager\nPassword: m111");
  }

  @FXML
  private void onNotManager() {
    viewHandler.openView("main");
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Login");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}


