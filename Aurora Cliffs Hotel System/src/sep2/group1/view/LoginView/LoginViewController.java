package sep2.group1.view.LoginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.LoginViewModel;

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



/*OLD CODE DONT TOUCH*/

/*package sep2.group1.view.LoginView;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.LoginViewModel;

public class LoginViewController
{
  @FXML private TextField usernameTextField;
  @FXML private PasswordField passwordPasswordField;
  @FXML private Button loginButton;
  @FXML private Button forgotLoginButton;
  @FXML private Button notManagerButton;

  public void initialize()
  {
    usernameTextField.setText("");
    passwordPasswordField.setText("");
  }

  public void reset()
  {
    usernameTextField.clear();
    passwordPasswordField.clear();
  }

  @FXML private void onLogin(ActionEvent event)
  {
    String usernameTextField = this.usernameTextField.getText();
    String passwordPasswordField = this.passwordPasswordField.getText();

    // For Empty Field Validation
    if (usernameTextField.isEmpty() || passwordPasswordField.isEmpty())
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields must be completed.");
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.showAndWait();
      return;
    }

    /*
    Temporary login

    Username - manager
    Password - m111

    if (usernameTextField.equals("manager") && passwordPasswordField.equals("m111"))
    {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login successful.");
      alert.setTitle("Success");
      alert.setHeaderText(null);
      alert.showAndWait();

      // Open Window
      try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/sep2/group1/view/ManagerView/ManagerView.fxml")
        );

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Hotel Aurora Cliffs");
        stage.setScene(new Scene(root));
        stage.show();

      } catch (Exception a) {
        a.printStackTrace();
      }

      // Close Window
      ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }


    else
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
      alert.setTitle("Login error");
      alert.setHeaderText(null);
      alert.showAndWait();
    }
  }

  // Username and Password reminder button function
  @FXML private void onForgotLogin(ActionEvent event)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Username: manager \nPassword: m111");
    alert.setTitle("Login");
    alert.setHeaderText("LOGIN");
    alert.showAndWait();
  }

  // Not Manager button function
  @FXML private void onNotManager(ActionEvent event)
  {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/sep2/group1/view/MainView/MainPage.fxml")
      );

      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Hotel Aurora Cliffs");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (Exception a) {
      a.printStackTrace();
    }

    // Close Window
    ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
  }


  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel)
  {
  }
}*/

