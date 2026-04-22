package sep2.group1.view.LoginView;

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
  @FXML private TextField userName;
  @FXML private PasswordField password;
  @FXML private Button login;

  public void initialize()
  {
    userName.setText("");
    password.setText("");
  }

  public void reset()
  {
    userName.clear();
    password.clear();
  }

  @FXML private void onLogin(ActionEvent event)
  {
    String userName = this.userName.getText();
    String password = this.password.getText();

    // For Empty Field Validation
    if (userName.isEmpty() || password.isEmpty())
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
     */
    if (userName.equals("manager") && password.equals("m111"))
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

  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel)
  {
  }
}
