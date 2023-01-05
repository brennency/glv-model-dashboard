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
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        </div>
        <div id="blur-window">
            <div id="login-container">
                <form action="Login" method="POST">
                    <div class="form-input">
                        <p>Username:</p> 
                        <input type="text" name="username" value="username"/>
                    </div>
                    <div class="form-input">
                        <p>Password:</p>
                        <input type="password" name="password" value="password"/>
                    </div>
                    <button>Log in</button>
                </form>
                <div id="login-error">
                <% if (loginRedirect != null) { %>
                    <p><%= loginRedirect %></p>
                <% } %>
                </div>
            </div>
        </div>
    </body>
</html>