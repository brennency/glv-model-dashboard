package abc.auth;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = 
    {"/home.jsp", "/about.jsp", "/Model", "/Params"})
public class AuthFilter implements Filter {

    public void redirectToLogin(HttpServletRequest req, HttpServletResponse res, String message) 
        throws IOException {

        HttpSession session = req.getSession();

        session.setAttribute("login-redirect", message);
        res.sendRedirect("login.jsp");
        return;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws ServletException, IOException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        ServletContext ctx = req.getServletContext();
        
        Cookie[] cookies = req.getCookies();
        ArrayList<String> userSessions = (ArrayList<String>) ctx.getAttribute("userSessions");
        boolean isAuthorized = false;
        
        if (cookies != null) { 
            for (Cookie c : cookies) {
                if ("user".equals(c.getName())) {
                    String userSessionId = c.getValue();
                    isAuthorized = userSessions.contains(userSessionId);
                }
            }
        }
        
        if (isAuthorized) {
            // Disables caching for protected routes
            res.setHeader("Pragma", "No-cache");
            res.setHeader("Cache-control", "no-cache");
            res.setDateHeader("Expires", 0);

            chain.doFilter(request, response);
        } else {
            redirectToLogin(req, res, "Please log in");
        }
    }
}
