<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Account" %>
<% String error = (String) request.getAttribute("error");  %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
  </head>
  <body>
    <h1>Sign in</h1>
    <% if (error != null) { %>
      <p style="color: red"><%= error %></p>
    <% } %>
    <form action="sign-in" method="post">
      <p>
        <label for="username">
          Username:
          <input type="text" name="username" id="username" required />
        </label>
      </p>
      <p>
        <label for="password">
          Password:
          <input type="password" name="password" id="password" required />
        </label>
      </p>
      <p>
        Don't have an account? <a href="sign-up">Sign up</a>
      </p>
      <p>
        Forgot your password? <a href="reset-password">Reset password</a>
      </p>
      <button type="submit">Sign in</button>
    </form>
  </body>
</html>
