package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import services.AccountService;

@WebServlet(urlPatterns = "/sign-up")
public class SignUpController extends HttpServlet {
  private final static long serialVersionUID = 1L;
  private AccountService accountService = AccountService.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String password = req.getParameter("password");
    String confirmPassword = req.getParameter("confirm-password");

    if (!password.equals(confirmPassword)) {
      req.setAttribute("error", "Password and confirm password do not match");
      req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
      return;
    }

    String username = req.getParameter("username");
    if (accountService.checkUsernameExists(username)) {
      req.setAttribute("error", "Username is already taken");
      req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
      return;
    }

    String email = req.getParameter("email");
    if (accountService.checkEmailExists(email)) {
      req.setAttribute("error", "Email is already taken");
      req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
      return;
    }

    accountService.signUp(username, confirmPassword, email);

    // Redirect to sign-in page using POST method
    // 307: Temporary Redirect (keeping the same HTTP method, POST in this case)
    resp.setStatus(307);
    resp.setHeader("Location", "sign-in");
  }
}