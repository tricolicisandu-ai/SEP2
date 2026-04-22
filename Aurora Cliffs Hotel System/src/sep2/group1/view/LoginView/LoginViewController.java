package sep2.group1.view.LoginView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.Event;

public class LoginViewController
{
  @FXML private TextField userName;
  @FXML private PasswordField password;

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

  @FXML private void Login(Event e)
  {
    String userName = this.userName.getText();
    String password = this.password.getText();

    //For empty field validation
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

      reset();
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
      alert.setTitle("Login error");
      alert.setHeaderText(null);
      alert.showAndWait();
    }
  }
    
}
