<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Account" %>
<% String error = (String) request.getAttribute("error");  %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Forgot password</title>
</head>
<body>
  <h1>Forgot password</h1>
  <% if (error != null) { %>
    <p style="color: red"><%= error %></p>
  <% } %>
  <form action="reset-password" method="post">
    <p>
      <label for="email">
        Email:
        <input type="text" id="email" name="email" required>
      </label>
    </p>
    <button type="submit">Confirm</button>
  </form>
</body>
</html>