package sep2.group1.model;

public class Room
{
  private int roomNumber;
  private int numberOfBeds;
  private double price;
  private int numberOfGuest;

  public Room(int roomNumber, int numberOfBeds, int price, int numberOfGuest){
    this.roomNumber=roomNumber;
    this.numberOfBeds=numberOfBeds;
    this.price=price;
    this.numberOfGuest=numberOfGuest;
  }

  public void setRoomNumber(int roomNumber)
  {
    this.roomNumber = roomNumber;
  }

  public void setNumberOfBeds(int numberOfBeds)
  {
    this.numberOfBeds = numberOfBeds;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public void setNumberOfGuest(int numberOfGuest)
  {
    this.numberOfGuest = numberOfGuest;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public int getNumberOfBeds()
  {
    return numberOfBeds;
  }

  public double getPrice()
  {
    return price;
  }

  public int getNumberOfGuest()
  {
    return numberOfGuest;
  }
}