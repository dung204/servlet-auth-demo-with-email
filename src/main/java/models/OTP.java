package models;

public class OTP {
  private int accountId;
  private String code;

  public OTP(int accountId, String code) {
    this.accountId = accountId;
    this.code = code;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}