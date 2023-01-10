package abc.auth;

import java.util.ArrayList;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import abc.dao.ModelDao;

@WebListener
public class AppStartListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ModelDao dao = new ModelDao();

        dao.createModel();
        dao.setModelParameters(dao.sampleParams);
        dao.model.run();

        // Create list of active user sessions stored at the context level
        servletContext.setAttribute("userSessions", new ArrayList<String>());

        servletContext.setAttribute("dao", dao);
    }
}
