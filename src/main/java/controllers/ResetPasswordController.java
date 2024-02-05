package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import models.Account;
import services.AccountService;
import services.OTPService;

@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet {
  private final static long serialVersionUID = 1L;
  private AccountService accountService = AccountService.getInstance();
  private OTPService otpService = OTPService.getInstance();

  private final String RESET_PASSWORD_STEP_ATTRIBUTE = "reset-password-step";
  private final String RESET_PASSWORD_ACCOUNT_ATTRIBUTE = "reset-password-account";
  private final String STEP_1_JSP = "reset-password/step-1.jsp";
  private final String STEP_2_JSP = "reset-password/step-2.jsp";
  private final String STEP_3_JSP = "reset-password/step-3.jsp";
  private final String STEP_X_JSP = "reset-password/step-%s.jsp";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    int step = (int) session.getAttribute(RESET_PASSWORD_STEP_ATTRIBUTE);

    session.setAttribute(RESET_PASSWORD_STEP_ATTRIBUTE, step);
    req.getRequestDispatcher(STEP_X_JSP.formatted(step)).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int step = (int) req.getSession().getAttribute(RESET_PASSWORD_STEP_ATTRIBUTE);

    switch (step) {
      case 1:
        handleStep1(req, resp);
        break;
      case 2:
        handleStep2(req, resp);
        break;
      case 3:
        handleStep3(req, resp);
        break;
      default:
        req.getRequestDispatcher(STEP_1_JSP).forward(req, resp);
    }
  }

  private void handleStep1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");

    Account account = accountService.getAccountByEmail(email);
    if (account == null) {
      req.setAttribute("error", "Invalid email");
      req.getRequestDispatcher(STEP_1_JSP).forward(req, resp);
      return;
    }

    otpService.createOTP(account);

    HttpSession session = req.getSession();
    session.setAttribute(RESET_PASSWORD_ACCOUNT_ATTRIBUTE, account);
    session.setAttribute(RESET_PASSWORD_STEP_ATTRIBUTE, 2);
    req.getRequestDispatcher(STEP_2_JSP).forward(req, resp);
  }

  private void handleStep2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    Account account = (Account) session.getAttribute(RESET_PASSWORD_ACCOUNT_ATTRIBUTE);
    String otpCode = req.getParameter("otp-code");

    if (!otpService.verifyOTP(account.getId(), otpCode)) {
      req.setAttribute("error", "OTP code is incorrect");
      req.getRequestDispatcher(STEP_2_JSP).forward(req, resp);
      return;
    }

    session.setAttribute(RESET_PASSWORD_STEP_ATTRIBUTE, 3);
    req.getRequestDispatcher(STEP_3_JSP).forward(req, resp);
  }

  private void handleStep3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    Account account = (Account) session.getAttribute(RESET_PASSWORD_ACCOUNT_ATTRIBUTE);
    String newPassword = req.getParameter("new-password");
    String confirmPassword = req.getParameter("confirm-new-password");

    if (!newPassword.equals(confirmPassword)) {
      req.setAttribute("error", "Passwords do not match");
      req.getRequestDispatcher(STEP_3_JSP).forward(req, resp);
      return;
    }

    accountService.updatePassword(account, newPassword);

    session.removeAttribute(RESET_PASSWORD_STEP_ATTRIBUTE);
    session.removeAttribute(RESET_PASSWORD_ACCOUNT_ATTRIBUTE);

    req.getRequestDispatcher("reset-password/success.jsp").forward(req, resp);
  }

}