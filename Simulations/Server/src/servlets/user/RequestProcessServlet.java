package servlets.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RequestDetailsDTO;
import dto.TerminateConditionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;
import utils.user.RequestsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RequestProcessServlet", urlPatterns = "/request-process-user-servlet")
public class RequestProcessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = SessionUtils.getUsername(req);
        if (username == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String simulationName = req.getParameter("simulationName");
            String amountOfRunning = req.getParameter("amountOfRunning");
            String byUser = req.getParameter("byUser");
            String byTicks = req.getParameter("byTicks");
            String bySeconds = req.getParameter("bySeconds");
            TerminateConditionDTO terminateConditionDTO = null;
            if (byUser != null) {
                terminateConditionDTO = new TerminateConditionDTO(null, null, "by user");
            } else if (byTicks != null && bySeconds != null) {
                terminateConditionDTO = new TerminateConditionDTO
                        (Integer.parseInt(bySeconds), Integer.parseInt(byTicks), null);
            } else if (byTicks != null) {
                terminateConditionDTO = new TerminateConditionDTO(null, Integer.parseInt(byTicks), null);
            } else if (bySeconds != null) {
                terminateConditionDTO = new TerminateConditionDTO((Integer.parseInt(bySeconds)), null, null);
            }

            RequestDetailsDTO requestDetailsDTO;
            List<TerminateConditionDTO> terminateConditions = new ArrayList<>();
            if (terminateConditionDTO != null) {
                terminateConditions.add(terminateConditionDTO);
                requestDetailsDTO = new
                        RequestDetailsDTO(username,(Integer)getServletContext().getAttribute("requestsCounter"),
                        simulationName, Integer.parseInt(amountOfRunning),"pending",0,0, terminateConditions);
                ((RequestsManager) getServletContext().getAttribute("requestDetailsManager")).addRequestByUser(username, requestDetailsDTO);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonOutput = gson.toJson(((RequestsManager) getServletContext().getAttribute("requestDetailsManager")).getAllRequestsByUser(username));
                resp.setContentType("application/json");
                resp.getWriter().write(jsonOutput);
                getServletContext().setAttribute("requestsCounter", (Integer)getServletContext().getAttribute("requestsCounter") + 1);
            } else {
                resp.getWriter().println(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
