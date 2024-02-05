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

@WebServlet(urlPatterns = "/sign-in")
public class SignInController extends HttpServlet {
  private final static long serialVersionUID = 1L;
  private AccountService accountService = AccountService.getInstance();

  @Override
  public void init() throws ServletException {
    super.init();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("sign-in.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    resp.setContentType("text/html");

    Account account = accountService.signIn(username, password);

    if (account != null) {
      HttpSession session = req.getSession(true);
      session.setAttribute("account", account);
      resp.sendRedirect("home.jsp");
      return;
    }

    req.setAttribute("error", "Username or password is incorrect");
    req.getRequestDispatcher("sign-in.jsp").forward(req, resp);
  }
}