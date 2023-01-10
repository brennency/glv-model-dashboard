package abc.auth;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import abc.dao.LoginDao;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
            
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        LoginDao loginDao = new LoginDao();

        ServletContext ctx = req.getServletContext();
        ArrayList<String> userSessions = (ArrayList<String>) ctx.getAttribute("userSessions");


        if (loginDao.validateLogin(username, password)) {
            String sessionToken = UUID.randomUUID().toString();
            String sessionUserIdCookie = "user="+ sessionToken + ";HttpOnly"; 
            res.addHeader("Set-Cookie", sessionUserIdCookie);
            userSessions.add(sessionToken);
            ctx.setAttribute("userSessions", userSessions);

            res.sendRedirect("home.jsp");
        } 
        else {
            HttpSession session = req.getSession();
            session.setAttribute("login-redirect", "Incorrect username or password");
            res.sendRedirect("login.jsp");
        }
    }
}
