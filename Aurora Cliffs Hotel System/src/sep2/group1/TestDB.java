package sep2.group1;

import sep2.group1.DatabaseConnection;

public class TestDB {
  public static void main(String[] args) {
    try {
      Class.forName("org.postgresql.Driver");
      var conn = DatabaseConnection.getConnection();
      System.out.println("CONNECTED");
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
