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
import utils.user.RequestsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RequestProcessServlet", urlPatterns = "/request-process-servlet")
public class RequestProcessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String simulationName = req.getParameter("simulationName");
        String amountOfRunning = req.getParameter("amountOfRunning");
        String byUser = req.getParameter("byUser");
        String byTicks = req.getParameter("byTicks");
        String bySeconds = req.getParameter("bySeconds");
        TerminateConditionDTO terminateConditionDTO = null;
        if(byUser != null){
            terminateConditionDTO = new TerminateConditionDTO(null, null, "by user");
        }
        else if(byTicks != null && bySeconds != null){
            terminateConditionDTO = new TerminateConditionDTO
                    (Integer.parseInt(bySeconds), Integer.parseInt(byTicks), null);
        }
        else if(byTicks != null){
            terminateConditionDTO = new TerminateConditionDTO(null, Integer.parseInt(byTicks), null);
        }
        else if(bySeconds != null){
            terminateConditionDTO = new TerminateConditionDTO((Integer.parseInt(bySeconds)), null, null);
        }

        RequestDetailsDTO requestDetailsDTO;
        List<TerminateConditionDTO> terminateConditions = new ArrayList<>();
        if(terminateConditionDTO != null){
            terminateConditions.add(terminateConditionDTO);
            requestDetailsDTO = new
                    RequestDetailsDTO(simulationName, Integer.parseInt(amountOfRunning), terminateConditions);
            ((RequestsManager)getServletContext().getAttribute("requestDetailsManager")).addRequest(requestDetailsDTO);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(((RequestsManager)getServletContext().getAttribute("requestDetailsManager")).getAllRequests());
            resp.setContentType("application/json");
            resp.getWriter().write(jsonOutput);
        }
        else{
            resp.getWriter().println(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
