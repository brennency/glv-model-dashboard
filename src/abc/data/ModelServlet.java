package abc.data;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import abc.dao.ModelDao;
import abc.model.GeneralizedLotkaVolterra.Parameters;



@WebServlet("/Model")
public class ModelServlet extends HttpServlet {
    
    // sends JSON data as response
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
            
        ModelDao dao = (ModelDao) req.getServletContext().getAttribute("dao");
        String jsonData = dao.getJSON();
        
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.print(jsonData);
    }

    // updates model values in the DAO
    public void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException {
    
        ModelDao dao = (ModelDao) req.getServletContext().getAttribute("dao");
        Parameters updatedParams = (Parameters) req.getAttribute("default-params");
        
        dao.setModelParameters(updatedParams);
        dao.model.run();

        req.setAttribute("default-view", "plot");
        req.getRequestDispatcher("home.jsp").forward(req, res);
    }
}
