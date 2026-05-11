package sep2.group1;

import javafx.application.Application;
import javafx.stage.Stage;
import sep2.group1.client.Client;
import sep2.group1.client.view.ViewHandler;
import sep2.group1.client.viewmodel.ViewModelFactory;

public class StartApplication extends Application {

  @Override
  public void start(Stage primaryStage) {

    // ---------------- CLIENT INIT ----------------
    try {
      Client.getInstance().connect();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // ---------------- EVENT HANDLER ----------------
    Client.getInstance().addEventHandler((String msg) -> {

      if (msg.equals("RESERVATION_CHANGED")) {
        System.out.println("Server update received");

        // refresh
      }
    });

    // ---------------- UI ----------------
    ViewHandler viewHandler = new ViewHandler();
    ViewModelFactory factory = new ViewModelFactory(viewHandler);

    viewHandler.setFactory(factory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}