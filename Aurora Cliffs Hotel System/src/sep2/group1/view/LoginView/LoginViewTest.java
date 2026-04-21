package sep2.group1.view.LoginView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginViewTest extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginView.fxml")));

    Scene scene = new Scene(root);

    stage.setTitle("Hotel Aurora Cliffs");
    stage.setScene(scene);
    stage.show();
  }
}