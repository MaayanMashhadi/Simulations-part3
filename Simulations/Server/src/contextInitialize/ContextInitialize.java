package contextInitialize;

import facade.Facade;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import utils.SimulationsDefinitionsManager;

@WebListener
public class ContextInitialize implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext()
                .setAttribute("simulationsDefinitionsManager",  new SimulationsDefinitionsManager());
        servletContextEvent.getServletContext().setAttribute("facade", new Facade());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
