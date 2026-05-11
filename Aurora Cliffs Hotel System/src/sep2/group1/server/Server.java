package sep2.group1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

  private static List<ClientHandler> clients =
      new ArrayList<>();

  public static void addClient(ClientHandler client) {
    clients.add(client);
  }

  public static void broadcast(String msg) {
    for (ClientHandler c : clients) {
      c.send(msg);
    }
  }

  public static void main(String[] args) {

    try {

      ServerSocket serverSocket =
          new ServerSocket(1234);

      System.out.println(
          "Server is running on port 1234..."
      );

      while (true) {

        Socket socket =
            serverSocket.accept();

        System.out.println(
            "Client has connected!"
        );

        ClientHandler handler =
            new ClientHandler(socket);

        addClient(handler);

        new Thread(handler).start();
      }

    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}