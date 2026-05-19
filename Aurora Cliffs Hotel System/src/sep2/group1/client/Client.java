package sep2.group1.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep2.group1.server.model.Reservation;
import sep2.group1.server.model.Room;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Client {

  private static Client instance;

  public static Client getInstance() {

    if (instance == null) {
      instance = new Client();
    }

    return instance;
  }

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  private final List<Consumer<String>> eventHandlers =
      new ArrayList<>();

  // SAFE MESSAGE QUEUE
  private final BlockingQueue<String> responses =
      new LinkedBlockingQueue<>();

  private Client() {}

  // CONNECT
  public void connect() throws IOException {

    if (socket != null && socket.isConnected()) {
      return;
    }

    socket = new Socket("localhost", 1234);

    in = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

    out = new PrintWriter(
        socket.getOutputStream(),
        true);

    startListener();
  }
  // EVENT HANDLERS
  public void addEventHandler(
      Consumer<String> handler) {

    eventHandlers.add(handler);
  }

  // LISTENER THREAD
  private void startListener() {

    Thread listener = new Thread(() -> {

      try {

        String msg;

        while ((msg = in.readLine()) != null) {

          // SERVER EVENT
          if (msg.equals("RESERVATION_CHANGED")  || msg.startsWith("ROOM_RESERVED")) {

            for (Consumer<String> handler :
                eventHandlers) {

              String finalMsg = msg;

              Platform.runLater(() ->
                  handler.accept(finalMsg));
            }
          }

          // NORMAL RESPONSE
          else {

            responses.put(msg);
          }
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }

    });

    listener.setDaemon(true);
    listener.start();
  }


  // WAIT RESPONSE

  private String waitResponse() {

    try {

      return responses.take();

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
  // GET ROOMS
  public synchronized ObservableList<Room> getRooms(
      LocalDate checkIn,
      LocalDate checkOut) {

    out.println(
        "GET_ROOMS,"
            + checkIn + ","
            + checkOut
    );

    ObservableList<Room> rooms =
        FXCollections.observableArrayList();

    try {

      String line;

      while (true) {

        line = waitResponse();

        if (line.equals("END")) break;

        String[] p = line.split(",");

        rooms.add(new Room(
            Integer.parseInt(p[0]),
            p[1],
            Integer.parseInt(p[2]),
            Double.parseDouble(p[3])
        ));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return rooms;
  }
  // GET RESERVATIONS
  public synchronized ObservableList<Reservation>
  getReservations() {

    ObservableList<Reservation> list =
        FXCollections.observableArrayList();

    try {

      out.println("GET_RESERVATIONS");

      while (true) {

        String line = waitResponse();

        if (line == null) {
          break;
        }

        if (line.equals("END")) {
          break;
        }

        String[] p = line.split(",");

        if (p.length < 6) {
          continue;
        }

        list.add(new Reservation(
            Integer.parseInt(p[0]),
            Integer.parseInt(p[1]),
            p[2],
            LocalDate.parse(p[3]),
            LocalDate.parse(p[4]),
            p[5],
            1,
            "",
            ""
        ));
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  // RESERVE
  public synchronized String reserveRoom(
      int roomNumber,
      String firstName,
      String lastName,
      String email,
      LocalDate checkIn,
      LocalDate checkOut,
      int guests) {

    try {

      out.println(
          "RESERVE,"
              + roomNumber + ","
              + firstName + ","
              + lastName + ","
              + email + ","
              + checkIn + ","
              + checkOut + ","
              + guests
      );

      return waitResponse();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return "ERROR";
  }
  // DELETE
  public synchronized void deleteReservation(
      int id) {

    try {

      out.println(
          "DELETE_RESERVATION," + id);

      waitResponse();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateReservationStatus(
      int reservationNumber,
      String status) {

    out.println(
        "UPDATE_STATUS,"
            + reservationNumber + ","
            + status
    );

    waitResponse();
  }
}