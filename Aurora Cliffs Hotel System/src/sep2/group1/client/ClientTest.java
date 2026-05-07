package sep2.group1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest
{
  public static void main (String[] args)
  {
    try
    {
      Socket socket = new Socket("10.154.208.84", 1234);
      System.out.println("connected");
      PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("Hi server");
      String response = in.readLine();
      System.out.println("Server: "+ response);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }

  }
}
