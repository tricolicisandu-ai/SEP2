package sep2.group1.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

  private Client client;

  @BeforeEach
  void setup() throws IOException {
    client = Client.getInstance();
    client.connect();
  }

  @Test
  void getRooms() {
    var rooms = client.getRooms(
        LocalDate.now(),
        LocalDate.now().plusDays(1)
    );

    assertNotNull(rooms, "Rooms should not be null");
  }

  @Test
  void getReservations() {
    var reservations = client.getReservations();

    assertNotNull(reservations, "Reservations should not be null");
  }

  @Test
  void reserveRoom() {
    String result = client.reserveRoom(
        1,
        "Test",
        "User",
        "test@mail.com",
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        2
    );

    assertTrue(
        result.equals("OK") || result.equals("NOT_AVAILABLE"),
        "Reservation should return OK or NOT_AVAILABLE"
    );
  }
}