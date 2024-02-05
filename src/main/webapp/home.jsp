<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="models.Account" %> 
<% Account account = (Account) session.getAttribute("account"); %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
  </head>
  <body>
    <h1>Home</h1>
    <p>Welcome, <%= account.getUsername() %></p>
    <form action="logout" method="post">
      <button type="submit">Logout</button>
    </form>
  </body>
</html>
