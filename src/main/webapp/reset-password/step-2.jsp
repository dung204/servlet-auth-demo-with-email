<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Account" %>
<% String error = (String) request.getAttribute("error");  %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enter your OTP code</title>
</head>
<body>
  <h1>Enter your OTP code</h1>
    <% if (error != null) { %>
      <p style="color: red"><%= error %></p>
    <% } %>
  <p>An OTP has been sent to your email, the OTP code will be expired in 5 minutes</p>
  <form action="reset-password" method="post">
    <p>
      <label for="otp-code">
        OTP code:
        <input type="text" id="otp-code" name="otp-code" placeholder="Enter your OTP here..." required>
      </label>
    </p>
    <button type="submit">Confirm</button>
  </form>
</body>
</html>