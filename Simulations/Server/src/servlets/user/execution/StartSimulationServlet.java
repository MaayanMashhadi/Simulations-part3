package servlets.user.execution;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ActiveEnvironmentDTO;
import facade.Facade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/start-simulation")
public class StartSimulationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String environmentManager = req.getParameter("environmentManager");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ActiveEnvironmentDTO activeEnvironmentDTO = gson.fromJson(environmentManager, ActiveEnvironmentDTO.class);
        Facade facade = (Facade) getServletContext().getAttribute("facade");
        facade.startSimulation(activeEnvironmentDTO);
        resp.getWriter().println("Start simulation successful");
    }
}
