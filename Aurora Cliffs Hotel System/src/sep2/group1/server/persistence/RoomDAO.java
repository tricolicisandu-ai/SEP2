package sep2.group1.server.persistence;

import sep2.group1.server.model.Room;
import sep2.group1.DatabaseConnection;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomDAO {

  public ObservableList<Room> getAllRooms() {
    ObservableList<Room> rooms = FXCollections.observableArrayList();

    String sql = "SELECT * FROM rooms";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Room room = new Room(
            rs.getInt("room_number"),
            rs.getString("room_type"),
            rs.getInt("beds"),
            rs.getDouble("price"));

        rooms.add(room);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return rooms;
  }
}