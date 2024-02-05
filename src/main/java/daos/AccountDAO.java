package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dal.DBContext;
import models.Account;

public final class AccountDAO {
  private final Connection connection = DBContext.getInstance().getConnection();
  private final Logger logger = Logger.getLogger(AccountDAO.class.getName());

  private static AccountDAO instance = null;

  private AccountDAO() {
  }

  public static AccountDAO getInstance() {
    if (instance == null) {
      instance = new AccountDAO();
    }
    return instance;
  }

  public List<Account> getAll() {
    List<Account> accounts = new ArrayList<>();

    try {
      String query = "SELECT * FROM Accounts";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet sqlResult = statement.executeQuery();

      while (sqlResult.next()) {
        int id = sqlResult.getInt("id");
        String username = sqlResult.getString("username");
        String password = sqlResult.getString("password");
        String email = sqlResult.getString("email");

        Account account = new Account(id, username, password, email);
        accounts.add(account);
      }

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return accounts;
  }

  public Account getOneByUsernameAndPassword(String username, String password) {
    try {
      String query = "SELECT * FROM Accounts WHERE username = ? AND password = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet sqlResult = statement.executeQuery();

      if (sqlResult.next()) {
        int id = sqlResult.getInt("id");
        String email = sqlResult.getString("email");
        return new Account(id, username, password, email);
      }

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  public Account getOneByUsername(String username) {
    try {
      String query = "SELECT * FROM Accounts WHERE username = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, username);
      ResultSet sqlResult = statement.executeQuery();

      if (sqlResult.next()) {
        int id = sqlResult.getInt("id");
        String password = sqlResult.getString("password");
        String email = sqlResult.getString("email");
        return new Account(id, username, password, email);
      }

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  public Account getOneByEmail(String email) {
    try {
      String query = "SELECT * FROM Accounts WHERE email = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, email);
      ResultSet sqlResult = statement.executeQuery();

      if (sqlResult.next()) {
        int id = sqlResult.getInt("id");
        String username = sqlResult.getString("username");
        String password = sqlResult.getString("password");
        return new Account(id, username, password, email);
      }

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  public boolean createOne(Account account) {
    try {
      String query = "INSERT INTO Accounts (username, password, email) VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, account.getUsername());
      statement.setString(2, account.getPassword());
      statement.setString(3, account.getEmail());

      statement.executeUpdate();
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
      return false;
    }
    return true;
  }

  public void updateOne(Account account) {
    try {
      String query = "UPDATE Accounts SET username = ?, password = ?, email = ? WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, account.getUsername());
      statement.setString(2, account.getPassword());
      statement.setString(3, account.getEmail());
      statement.setInt(4, account.getId());

      statement.executeUpdate();
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }
}