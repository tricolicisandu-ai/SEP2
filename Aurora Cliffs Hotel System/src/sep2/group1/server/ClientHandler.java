package sep2.group1.server;

import sep2.group1.server.model.*;
import sep2.group1.server.persistence.ReservationDAO;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class ClientHandler implements Runnable {

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  public ClientHandler(Socket socket) {

    this.socket = socket;

    Server.addClient(this);
  }

  public void send(String msg) {

    if (out != null) {
      out.println(msg);
    }
  }

  @Override
  public void run() {

    try {

      in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));

      out = new PrintWriter(
          socket.getOutputStream(),
          true);

      String request;

      while ((request = in.readLine()) != null) {

        // ==================================================
        // GET ROOMS
        // ==================================================
        if (request.equals("GET_ROOMS")) {

          for (Room room : RoomManager.getRooms()) {

            boolean available = true;

            for (Reservation r :
                ReservationManager.getReservations()) {

              if (r.getRoomNumber()
                  == room.getRoomNumber()) {

                available = false;
                break;
              }
            }

            // SEND ONLY FREE ROOMS
            if (available) {

              out.println(
                  room.getRoomNumber() + ","
                      + room.getRoomType() + ","
                      + room.getNumberOfBeds() + ","
                      + room.getPrice()
              );
            }
          }

          out.println("END");
        }

        // ==================================================
        // RESERVE
        // ==================================================
        else if (request.startsWith("RESERVE")) {

          String[] p = request.split(",");

          int roomNumber =
              Integer.parseInt(p[1]);

          String firstName = p[2];
          String lastName = p[3];
          String email = p[4];

          LocalDate checkIn =
              LocalDate.parse(p[5]);

          LocalDate checkOut =
              LocalDate.parse(p[6]);

          int guests =
              Integer.parseInt(p[7]);

          // CHECK OVERLAP
          boolean taken = false;

          for (Reservation r :
              ReservationManager.getReservations()) {

            if (r.getRoomNumber()
                == roomNumber) {

              boolean overlap =
                  !checkOut.isBefore(r.getCheckIn())
                      &&
                      !checkIn.isAfter(r.getCheckOut());

              if (overlap) {
                taken = true;
                break;
              }
            }
          }

          if (taken) {

            out.println("NOT_AVAILABLE");
            continue;
          }

          Reservation reservation =
              new Reservation(
                  0,
                  roomNumber,
                  email,
                  checkIn,
                  checkOut,
                  "Reserved",
                  guests,
                  firstName,
                  lastName
              );

          ReservationDAO dao =
              new ReservationDAO();

          dao.createReservation(reservation);

          ReservationManager.refreshReservations();

          out.println("OK");

          Server.broadcastExcept("ROOM_RESERVED," + roomNumber, this);
        }

        // ==================================================
        // GET RESERVATIONS
        // ==================================================
        else if (request.equals("GET_RESERVATIONS")) {

          ReservationDAO dao =
              new ReservationDAO();

          for (Reservation r :
              dao.getAllReservations()) {

            out.println(
                r.getReservationNumber() + ","
                    + r.getRoomNumber() + ","
                    + r.getEmail() + ","
                    + r.getCheckIn() + ","
                    + r.getCheckOut() + ","
                    + r.getStatus()
            );
          }

          out.println("END");
        }

        // ==================================================
        // DELETE
        // ==================================================
        else if (
            request.startsWith(
                "DELETE_RESERVATION")) {

          String[] p =
              request.split(",");

          int reservationNumber =
              Integer.parseInt(p[1]);

          ReservationDAO dao =
              new ReservationDAO();

          dao.deleteReservation(
              reservationNumber);

          ReservationManager.refreshReservations();

          out.println("DELETED");

          Server.broadcast(
              "RESERVATION_CHANGED");
        }
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}