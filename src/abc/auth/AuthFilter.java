package abc.auth;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;

import abc.dao.LoginDao;

@WebFilter(urlPatterns = 
    {"/home.jsp", "/about.jsp", "/Model", "/Model", "/Params"})
public class AuthFilter implements Filter {

    public void unauthorizedRedirect(HttpServletRequest req, HttpServletResponse res) 
        throws IOException {

        HttpSession session = req.getSession();

        session.setAttribute("login-redirect", "Unauthorized access");
        res.sendRedirect("login.jsp");
        return;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws ServletException, IOException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Disables caching for protected routes
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-control", "no-cache");
        res.setDateHeader("Expires", 0);

        Cookie[] cookies = req.getCookies();
        LoginDao loginDao = new LoginDao();
        boolean isAuthorized = false;

        if (cookies == null) { 
            unauthorizedRedirect(req, res);
            return;
        }

        for (Cookie c : cookies) {
            if ("authUser".equals(c.getName())) {
                String username = c.getValue();
                if (loginDao.isValidUser(username)) {
                    isAuthorized = true;
                }
            }
        }

        if (isAuthorized) {
            chain.doFilter(request, response);
        } else {
            unauthorizedRedirect(req, res);
        }
    }
}
