package sep2.group1.client.viewmodel;

import sep2.group1.client.view.ViewHandler;

public class MaidLoginViewModel
{

  /*
  Temporary login

  Username - maid
  Password - m123
   */
  private ViewHandler viewHandler;

  public MaidLoginViewModel(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
  }
  public boolean login(String username, String password) {
    return "maid".equals(username) && "m123".equals(password);
  }
  public void openMaidView(String maidView) {
    viewHandler.openView("maid");
  }
}