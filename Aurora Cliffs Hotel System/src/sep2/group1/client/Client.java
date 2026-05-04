package sep2.group1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;


  //Client connects to server
  public void connect() throws IOException
  {
    try
    {
      socket = new Socket( "localhost", 1234);

      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void getRooms () throws IOException
  {
    out.println("GET_ROOMS");

    String line = in.readLine();

    while (!line.equals("END"))
    {
      System.out.println(line);
      line = in.readLine();
    }
  }
  public void reserveRoom(int roomNumber, String firstName, String lastName, String email) throws IOException
  {
    String command = "RESERVE," + roomNumber + "," + firstName + ","  + lastName + "," + "," + email;
     out.println(command);

     String response = in.readLine();
    System.out.println("Server:" + response);
  }
  public void close() throws  IOException
  {
    if (socket != null)
    {
      socket.close();
    }
  }
}
