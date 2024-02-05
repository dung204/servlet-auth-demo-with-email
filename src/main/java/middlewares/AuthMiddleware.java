package middlewares;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/*" }, filterName = "AuthFilter")
public class AuthMiddleware extends HttpFilter {
  @Override
  protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    String requestURI = req.getRequestURI();
    HttpSession session = req.getSession();

    boolean isAuthURI = requestURI.endsWith("sign-in") || requestURI.endsWith("sign-up")
        || requestURI.endsWith("reset-password");

    if (isAuthURI && session.getAttribute("account") != null) {
      res.sendRedirect("home.jsp");
      return;
    }

    if (!isAuthURI && session.getAttribute("account") == null) {
      res.sendRedirect("sign-in");
      return;
    }

    chain.doFilter(req, res);
  }

}
