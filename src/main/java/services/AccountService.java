package services;

import daos.AccountDAO;
import models.Account;

public final class AccountService {
  private static AccountDAO accountDAO = AccountDAO.getInstance();

  private static AccountService instance = null;

  private AccountService() {
  }

  public static AccountService getInstance() {
    if (instance == null) {
      return new AccountService();
    }
    return instance;
  }

  public Account signIn(String username, String password) {
    return accountDAO.getOneByUsernameAndPassword(username, password);
  }

  public boolean signUp(String username, String password, String email) {
    return accountDAO.createOne(new Account(username, password, email));
  }

  public boolean checkUsernameExists(String username) {
    return accountDAO.getOneByUsername(username) != null;
  }

  public boolean checkEmailExists(String email) {
    return accountDAO.getOneByEmail(email) != null;
  }

  public void updatePassword(Account account, String newPassword) {
    account.setPassword(newPassword);
    accountDAO.updateOne(account);
  }

  public Account getAccountByEmail(String email) {
    return accountDAO.getOneByEmail(email);
  }
}