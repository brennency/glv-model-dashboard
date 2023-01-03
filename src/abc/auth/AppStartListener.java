package abc.auth;

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
        dao.setModelParameters(dao.sampleParams[0]);
        dao.model.run();

        servletContext.setAttribute("dao", dao);
    }
}
