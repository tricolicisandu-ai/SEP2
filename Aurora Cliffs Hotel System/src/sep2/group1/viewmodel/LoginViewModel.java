package sep2.group1.viewmodel;

import sep2.group1.view.ViewHandler;

public class LoginViewModel {

  /*
  Temporary login

  Username - manager
  Password - m111
   */
  private ViewHandler viewHandler;

  public LoginViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
  }
  public boolean login(String username, String password) {
    return "manager".equals(username) && "m111".equals(password);
  }
  public void openManagerView(String managerView) {
    viewHandler.openView("manager");
  }
}