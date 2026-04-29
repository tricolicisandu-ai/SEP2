package sep2.group1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sep2.group1.view.ViewHandler;
import sep2.group1.viewmodel.ViewModelFactory;

import java.util.Objects;

public class StartApplication extends Application
{
  @Override
  public void start(Stage primaryStage) {

    ViewHandler viewHandler = new ViewHandler();
    ViewModelFactory factory = new ViewModelFactory(viewHandler);

    viewHandler.setFactory(factory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
