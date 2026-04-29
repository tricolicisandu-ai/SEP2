package sep2.group1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomManager {

  private static ObservableList<Room> rooms = FXCollections.observableArrayList();

  static {
    rooms.add(new Room(101, "Single Room", 1, 100, 1));
    rooms.add(new Room(102, "Single Room", 1, 100, 1));

    rooms.add(new Room(103, "Double Room", 2, 150, 2));
    rooms.add(new Room(104, "Double Room", 2, 150, 2));
    rooms.add(new Room(105, "Double Room", 2, 150, 2));

    rooms.add(new Room(106, "Twin Room", 2, 180, 2));
    rooms.add(new Room(107, "Twin Room", 2, 180, 2));

    rooms.add(new Room(108, "Double-Double Room", 4, 300, 4));
    rooms.add(new Room(109, "Double-Double Room", 4, 300, 4));

    rooms.add(new Room(110, "3-Family Room", 3, 200, 3));
    rooms.add(new Room(111, "4-Family Room", 4, 250, 4));
    rooms.add(new Room(112, "4-Family Room", 4, 250, 4));

    rooms.add(new Room(113, "Deluxe Room", 3, 450, 3));
    rooms.add(new Room(114, "Deluxe Room", 4, 500, 4));
  }

  public static ObservableList<Room> getRooms() {
    return rooms;
  }
}
