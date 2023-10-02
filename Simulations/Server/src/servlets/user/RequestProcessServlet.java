package servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RequestProcessServlet", urlPatterns = "/request-process-servlet")
public class RequestProcessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String simulationName = req.getParameter("simulationName");
        String amountOfRunning = req.getParameter("amountOfRunning");
    }
}
