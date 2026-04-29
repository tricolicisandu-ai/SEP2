package sep2.group1.model;

public class Guest
{
  private String firstName;
  private String lastName;
  private String email;

  public Guest (String firstName, String lastName, String email){
    this.firstName=firstName;
    this.lastName=lastName;
    this.email=email;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public boolean equals(Object object)
  {
    if (object == null || getClass()!= object.getClass())
    {
      return false;
    }
    Guest other = (Guest) object;
    return this.firstName.equals(other.firstName) &&
        this.lastName.equals(other.lastName) &&
        this.email.equals(other.email);
  }

  public String toString()
  {
    return firstName + " " + lastName + " ( " + email + ")";
  }
}

