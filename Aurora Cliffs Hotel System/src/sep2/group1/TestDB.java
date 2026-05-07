package sep2.group1;

import sep2.group1.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB {
  public static void main(String[] args) {
    try {
      Class.forName("org.postgresql.Driver");
      Connection testCon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotel", "postgres", "admin");
      //Class.forName("org.postgresql.Driver");
      //var conn = DatabaseConnection.getConnection();
      System.out.println("CONNECTED");

      testCon.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
