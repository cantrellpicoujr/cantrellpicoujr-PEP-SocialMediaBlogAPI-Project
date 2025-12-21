package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

  private AccountDAO accountDAO;

  public AccountService() {
    accountDAO = new AccountDAO();
  }

  public Account addAccount(Account account) {

    if (account.getUsername() != "" && account.getPassword().length() > 4) {
      return accountDAO.insertAccount(account);
    }
    
    return null;

  }

  public Account loginAccount(Account account) {
    return accountDAO.loginAccount(account);
  }
  
}
