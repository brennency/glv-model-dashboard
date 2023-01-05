package abc.auth;

import java.io.IOException;
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

        if (loginDao.validateLogin(username, password)) {
            Cookie authCookie = new Cookie("authUser", username);

            res.addCookie(authCookie);
            res.sendRedirect("home.jsp");
        } 
        else {
            HttpSession session = req.getSession();
            session.setAttribute("login-redirect", "Authentication Error");
            res.sendRedirect("login.jsp");
        }
    }
}
