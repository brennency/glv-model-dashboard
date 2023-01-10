package abc.auth;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
       
        String userSessionId = null; 
        
        Cookie[] cookies = req.getCookies();
        
        for (Cookie c : cookies) {
            if ("user".equals(c.getName())) {
                userSessionId = c.getValue();

                // nullify client cookie
                c.setValue("");
                c.setMaxAge(0);
                res.addCookie(c);
            }
        }
        
        ServletContext ctx = req.getServletContext();
        ArrayList<String> userSessions = (ArrayList<String>) ctx.getAttribute("userSessions");

        // Remove user session token
        ctx.setAttribute("userSessions", userSessions); 


        HttpSession session = req.getSession();
        session.setAttribute("login-redirect", "Logged out");

        res.sendRedirect("login.jsp");
    }
}
