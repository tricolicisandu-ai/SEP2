package sep2.group1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(1234);
      System.out.println("Server is running on port 1234...");

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client has connected!");

        ClientHandler handler = new ClientHandler(socket);
        new Thread(handler).start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
