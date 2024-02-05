<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String error = (String) request.getAttribute("error"); %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign up</title>
</head>
<body>
  <h1>Sign up</h1>
    <% if (error != null) { %>
      <p style="color: red"><%= error %></p>
    <% } %>
    <form action="sign-up" method="post">
      <p>
        <label for="email">
          Email:
          <input type="email" name="email" id="email" required />
        </label>
      </p>
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
        <label for="confirm-password">
          Confirm password:
          <input type="password" name="confirm-password" id="confirm-password" required />
        </label>
      </p>
      <p>
        Already have an account? <a href="sign-in">Sign in</a>
      </p>
      <button type="submit">Sign up</button>
    </form>
</body>
</html>