package middlewares;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Account;
import services.OTPService;

@WebFilter(urlPatterns = "/*")
public class ResetPasswordStepMiddleware extends HttpFilter {
  private OTPService otpService = OTPService.getInstance();

  @Override
  protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    if (!req.getRequestURI().endsWith("/reset-password")) {
      HttpSession session = req.getSession();
      session.setAttribute("reset-password-step", 1);

      Account account = (Account) session.getAttribute("reset-password-account");
      if (account != null)
        otpService.deleteOTP(account.getId());
    }

    chain.doFilter(req, res);
  }
}