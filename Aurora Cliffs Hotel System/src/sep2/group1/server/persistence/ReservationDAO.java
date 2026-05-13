package sep2.group1.server.persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep2.group1.DatabaseConnection;
import sep2.group1.server.model.Reservation;

import java.sql.*;

public class ReservationDAO {

  public int createReservation(Reservation r) {

    String sql = "INSERT INTO reservations (room_number, first_name, last_name, email, check_in, check_out, status, guests) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    int databaseId = -1;

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setInt(1, r.getRoomNumber());
      stmt.setString(2, r.getFirstName());
      stmt.setString(3, r.getLastName());
      stmt.setString(4, r.getEmail());
      stmt.setDate(5, java.sql.Date.valueOf(r.getCheckIn()));
      stmt.setDate(6, java.sql.Date.valueOf(r.getCheckOut()));
      stmt.setString(7, r.getStatus());
      stmt.setInt(8, r.getNumberOfGuests());

      stmt.executeUpdate();

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          databaseId = rs.getInt(1);
        }
      }

      System.out.println("Reservation saved to DB with ID: " + databaseId);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return databaseId;
  }

  public ObservableList<Reservation> getAllReservations() {

    ObservableList<Reservation> list = FXCollections.observableArrayList();

    String sql = "SELECT * FROM reservations";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {

        Reservation r = new Reservation(
            rs.getInt("reservation_number"),
            rs.getInt("room_number"),
            rs.getString("email"),
            rs.getDate("check_in").toLocalDate(),
            rs.getDate("check_out").toLocalDate(),
            rs.getString("status"),
            rs.getInt("guests"),
            rs.getString("first_name"),
            rs.getString("last_name")
        );

        list.add(r);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  public void updateStatus(
      int reservationNumber,
      String status) {

    try (
        Connection connection =
            DatabaseConnection.getConnection();

        PreparedStatement statement =
            connection.prepareStatement(
                "UPDATE reservations " +
                    "SET status=? " +
                    "WHERE reservation_number=?")
    ) {

      statement.setString(1, status);

      statement.setInt(2, reservationNumber);

      statement.executeUpdate();

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteReservation(int reservationId) {

    String sql = "DELETE FROM reservations WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, reservationId);
      stmt.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}