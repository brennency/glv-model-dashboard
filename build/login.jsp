<!DOCTYPE html>
<%! String loginRedirect; %>
<% 
    if (session.getAttribute("login-redirect") != null) {
        loginRedirect = (String) session.getAttribute("login-redirect");
    }

%>

<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="javascript/login-form.js"></script>
    </head>
    <body>
        <div id="login-container">
            <form id="login-form" action="Login" method="POST">
                <input id="auth-key" type="hidden">
                <div class="form-input">
                    <p>Username:</p> 
                    <input id="username" type="text" name="username" value="username"/>
                </div>
                <div class="form-input">
                    <p>Password:</p>
                    <input id="password" type="password" name="password" value="password"/>
                </div>
                <div id="login-button">Log in</div>
            </form>
            <div id="login-error">
            <%= loginRedirect == null ? "Please log in" : loginRedirect %>
            </div>
        </div>
    </body>
</html>