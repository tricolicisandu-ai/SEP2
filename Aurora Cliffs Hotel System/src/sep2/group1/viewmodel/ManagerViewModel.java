package sep2.group1.viewmodel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import sep2.group1.model.Reservation;
import sep2.group1.model.ReservationManager;

public class ManagerViewModel
{
  private ObservableList<Reservation> reservations;

  public ManagerViewModel()
  {
    reservations = ReservationManager.getReservations();
  }

  public ObservableList<Reservation> getReservations()
  {
    return reservations;
  }

  public void checkIn(Reservation reservation)
  {
    if(reservation != null)
    {
      reservation.setStatus("Occupied");
    }
  }

  public void cancelReservation(Reservation reservation)
  {
    if(reservation != null)
    {
      reservation.setStatus("Available");
      reservations.remove(reservation);
    }
  }

  public void checkOut(Reservation reservation)
  {
    if(reservation != null)
    {
      reservation.setStatus("In Maintenance");

      Thread thread = new Thread(() ->
      {
        try
        {
          Thread.sleep(3000);

          Platform.runLater(() ->
          {
            reservations.remove(reservation);
          });
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      });

      thread.setDaemon(true);
      thread.start();
    }
  }
}