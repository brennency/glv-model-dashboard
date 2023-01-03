package abc.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
       
        Cookie[] cookies = req.getCookies();

        for (Cookie c : cookies) {
            if ("authUser".equals(c.getName())) {
                c.setValue("");
                c.setMaxAge(0);
                res.addCookie(c);
            }
        }

        HttpSession session = req.getSession();
        session.setAttribute("login-redirect", "Logged out");

        res.sendRedirect("index.jsp");
    }
}
