package sep2.group1.server;

import sep2.group1.server.model.Guest;
import sep2.group1.server.model.Room;
import sep2.group1.server.model.RoomManager;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  public ClientHandler(Socket socket)
  {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      String request;

      while ((request = in.readLine()) != null) {

        if (request.equals("GET_ROOMS")) {
          for (Room room : RoomManager.getRooms()) {
            out.println(room.getRoomNumber() + "," + room.getRoomType());
          }
          out.println("END");
        }
        else if (request.startsWith("RESERVE")) {

          String[] parts = request.split(",");
          int roomNumber = Integer.parseInt(parts[1]);
          String firstName = parts[2];
          String lastName = parts[3];
          String email = parts[4];

          Guest guest = new Guest(firstName, lastName, email);

          for (Room room : RoomManager.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
              room.reserve(guest);
              break;
            }
          }

          out.println("OK");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}