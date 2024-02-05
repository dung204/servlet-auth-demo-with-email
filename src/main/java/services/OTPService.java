package services;

import daos.OTPDAO;
import models.Account;
import models.OTP;

public final class OTPService {
  private static final int OTP_TIMEOUT_SECONDS = 60 * 5;
  private static OTPDAO otpDAO = OTPDAO.getInstance();
  private static MailService mailService = MailService.getInstance();

  private static final OTPService instance = null;

  private OTPService() {
  }

  public static OTPService getInstance() {
    if (instance == null) {
      return new OTPService();
    }
    return instance;
  }

  public void createOTP(Account account) {
    String otp = generateOTP();

    OTP existingOTP = otpDAO.getOneByAccountId(account.getId());

    mailService.sendMail(account.getEmail(), "Your OTP code", "Your OTP code is: " + otp);

    if (existingOTP != null) {
      otpDAO.updateOneByAccountId(account.getId(), otp);
      setOTPTimeout(account.getId());
      return;
    }

    otpDAO.createOne(account.getId(), otp);
    setOTPTimeout(account.getId());
  }

  public boolean verifyOTP(int accountId, String otp) {
    OTP existingOTP = otpDAO.getOneByAccountIdAndCode(accountId, otp);

    if (existingOTP == null) {
      return false;
    }

    deleteOTP(accountId);
    return true;
  }

  public void deleteOTP(int accountId) {
    cancelOTPTimeout(accountId);
    otpDAO.deleteByAccountId(accountId);
  }

  private void setOTPTimeout(int accountId) {
    Thread thread = new Thread(() -> {
      try {
        Thread.sleep(OTP_TIMEOUT_SECONDS * 1000);
        otpDAO.deleteByAccountId(accountId);
      } catch (InterruptedException e) {
      }
    });

    thread.setName("otp_timeout_account_id_%s".formatted(accountId));
    thread.setDaemon(true);
    thread.start();
  }

  private void cancelOTPTimeout(int accountId) {
    Thread.getAllStackTraces()
        .keySet()
        .stream()
        .filter(thread -> thread.getName().equals("otp_timeout_account_id_%s".formatted(accountId)))
        .forEach(Thread::interrupt);
  }

  private String generateOTP() {
    // OTP code has 6 digits
    return String.valueOf((int) (Math.random() * 900000) + 100000);
  }
}