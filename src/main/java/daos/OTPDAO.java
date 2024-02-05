package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;

import dal.DBContext;
import models.OTP;

public final class OTPDAO {
  private final Connection connection = DBContext.getInstance().getConnection();
  private final Logger logger = Logger.getLogger(OTPDAO.class.getName());

  private static OTPDAO instance = null;

  private OTPDAO() {
  }

  public static OTPDAO getInstance() {
    if (instance == null) {
      instance = new OTPDAO();
    }
    return instance;
  }

  public void createOne(int accountId, String code) {
    try {
      String query = "INSERT INTO OTPs (account_id, code) VALUES (?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, accountId);
      statement.setString(2, code);
      statement.executeUpdate();
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  public void deleteByAccountId(int accountId) {
    try {
      String query = "DELETE FROM OTPs WHERE account_id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, accountId);
      statement.executeUpdate();
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  public OTP getOneByAccountIdAndCode(int accountId, String code) {
    try {
      String query = "SELECT * FROM OTPs WHERE account_id = ? AND code = ?";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setInt(1, accountId);
      statement.setString(2, code);

      ResultSet sqlResult = statement.executeQuery();

      if (sqlResult.next()) {
        return new OTP(sqlResult.getInt("account_id"), sqlResult.getString("code"));
      }
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  public OTP getOneByAccountId(int accountId) {
    try {
      String query = "SELECT * FROM OTPs WHERE account_id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, accountId);
      ResultSet sqlResult = statement.executeQuery();

      if (sqlResult.next()) {
        return new OTP(sqlResult.getInt("account_id"), sqlResult.getString("code"));
      }
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  public OTP updateOneByAccountId(int accountId, String code) {
    try {
      String query = "UPDATE OTPs SET code = ? WHERE account_id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, code);
      statement.setInt(2, accountId);
      statement.executeUpdate();
      return new OTP(accountId, code);
    } catch (SQLException ex) {
      logger.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }
}