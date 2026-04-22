package sep2.group1.viewmodel;

public class ViewModelFactory
{
  private MainPageViewModel mainPageViewModel;
  private ReservationViewModel reservationViewModel;
  private RoomDetailsViewModel roomDetailsViewModel;
  private ManagerViewModel managerViewModel;
  private LoginViewModel loginViewModel;

  public MainPageViewModel getMainPageViewModel()
  {
    if (mainPageViewModel == null)
      mainPageViewModel = new MainPageViewModel();
    return mainPageViewModel;
  }

  public ReservationViewModel getReservationViewModel()
  {
    if (reservationViewModel == null)
      reservationViewModel = new ReservationViewModel();
    return reservationViewModel;
  }

  public RoomDetailsViewModel getRoomDetailsViewModel()
  {
    if (roomDetailsViewModel == null)
      roomDetailsViewModel = new RoomDetailsViewModel();
    return roomDetailsViewModel;
  }

  public ManagerViewModel getManagerViewModel()
  {
    if (managerViewModel == null)
      managerViewModel = new ManagerViewModel();
    return managerViewModel;
  }

  public LoginViewModel getLoginViewModel()
  {
    if (loginViewModel == null)
      loginViewModel = new LoginViewModel();
    return loginViewModel;
  }
}
