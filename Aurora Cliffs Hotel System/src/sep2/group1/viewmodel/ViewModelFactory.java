package sep2.group1.viewmodel;

import sep2.group1.view.ViewHandler;

public class ViewModelFactory {

  private ViewHandler viewHandler;

  // Singletons for sharing data between windows
  private MainPageViewModel mainPageViewModel;
  private ReservationViewModel reservationViewModel;
  private RoomDetailsViewModel roomDetailsViewModel;
  private ManagerViewModel managerViewModel;
  private LoginViewModel loginViewModel;

  public ViewModelFactory(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
  }

  public MainPageViewModel getMainPageViewModel() {
    if (mainPageViewModel == null) {
      mainPageViewModel = new MainPageViewModel();
    }
    return mainPageViewModel;
  }

  public LoginViewModel getLoginViewModel() {
    if (loginViewModel == null) {
      loginViewModel = new LoginViewModel(viewHandler);
    }
    return loginViewModel;
  }

  public RoomDetailsViewModel getRoomDetailsViewModel() {
    if (roomDetailsViewModel == null) {
      roomDetailsViewModel = new RoomDetailsViewModel(viewHandler);
    }
    return roomDetailsViewModel;
  }

  public ManagerViewModel getManagerViewModel() {
    if (managerViewModel == null) {
      managerViewModel = new ManagerViewModel(viewHandler);
    }
    return managerViewModel;
  }

  public ReservationViewModel getReservationViewModel() {
    if (reservationViewModel == null) {
      reservationViewModel = new ReservationViewModel(viewHandler);
    }
    return reservationViewModel;
  }
}