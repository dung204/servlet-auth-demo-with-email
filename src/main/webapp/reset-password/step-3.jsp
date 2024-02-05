<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Reset your password</title>
</head>
<body>
  <h1>Reset Your Password</h1>
  <p>Enter your new password below to finish resetting your password</p>
  <form action="reset-password" method="post">
    <p>
      <label for="new-password">
        New password: 
        <input type="password" name="new-password" id="new-password" placeholder="New password here..." required>
      </label>
    </p>
    <p>
      <label for="confirm-new-password">
        Confirm new password: 
        <input type="password" name="confirm-new-password" id="confirm-new-password" required>
      </label>
    </p>
    <button type="submit">Reset Password</button>
  </form>
</body>
</html>