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

  private final List<Consumer<String>> eventHandlers = new ArrayList<>();

  // sync request/response
  private final Object lock = new Object();
  private String response;
  private boolean hasResponse = false;

  private Client() {}

  // ---------------- CONNECT ----------------
  public void connect() throws IOException {

    if (socket != null && socket.isConnected()) return;

    socket = new Socket("localhost", 1234);

    in = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

    out = new PrintWriter(socket.getOutputStream(), true);

    startListener();
  }

  // ---------------- EVENT HANDLERS ----------------
  public void addEventHandler(Consumer<String> handler) {
    eventHandlers.add(handler);
  }

  // ---------------- LISTENER THREAD ----------------
  private void startListener() {

    new Thread(() -> {

      try {

        String msg;

        while ((msg = in.readLine()) != null) {

          if (msg.equals("RESERVATION_CHANGED")) {

            for (Consumer<String> h : eventHandlers) {
              String finalMsg = msg;
              Platform.runLater(() -> h.accept(finalMsg));
            }

          } else {

            synchronized (lock) {
              response = msg;
              hasResponse = true;
              lock.notifyAll();
            }
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

    }).start();
  }

  // ---------------- WAIT RESPONSE ----------------
  private String waitResponse() {

    synchronized (lock) {

      while (!hasResponse) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      hasResponse = false;
      return response;
    }
  }

  // ---------------- ROOMS ----------------
  public ObservableList<Room> getRooms() {

    out.println("GET_ROOMS");

    ObservableList<Room> rooms =
        FXCollections.observableArrayList();

    try {

      String line;

      while (true) {

        line = waitResponse();

        if (line.equals("END")) break;

        String[] p = line.split(",");

        if (p.length < 4) continue;

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

  // ---------------- RESERVATIONS ----------------
  public ObservableList<Reservation> getReservations() {

    out.println("GET_RESERVATIONS");

    ObservableList<Reservation> list =
        FXCollections.observableArrayList();

    try {

      String line;

      while (true) {

        line = waitResponse();

        if (line.equals("END")) break;

        String[] p = line.split(",");

        if (p.length < 6) continue;

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

    } catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  // ---------------- RESERVE ----------------
  public String reserveRoom(
      int roomNumber,
      String firstName,
      String lastName,
      String email,
      LocalDate checkIn,
      LocalDate checkOut,
      int guests) {

    out.println("RESERVE,"
        + roomNumber + ","
        + firstName + ","
        + lastName + ","
        + email + ","
        + checkIn + ","
        + checkOut + ","
        + guests);

    return waitResponse(); // OK / NOT_AVAILABLE
  }

  // ---------------- DELETE ----------------
  public void deleteReservation(int id) {

    out.println("DELETE_RESERVATION," + id);
    waitResponse(); // DELETED
  }
}