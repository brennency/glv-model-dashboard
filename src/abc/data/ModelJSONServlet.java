package abc.data;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import abc.dao.ModelDao;


@WebServlet("/Model/JSON")
public class ModelJSONServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
            
        ModelDao dao = (ModelDao) req.getServletContext().getAttribute("dao");
        String jsonData = dao.getJSON();
        
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.print(jsonData);
    }
}