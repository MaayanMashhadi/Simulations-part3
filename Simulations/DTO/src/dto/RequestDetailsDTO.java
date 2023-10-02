package dto;

import java.util.List;

public class RequestDetailsDTO {
    private String simulationName;
    private int amountOfRunning;
    private List<TerminateConditionDTO> terminateConditions;

    public RequestDetailsDTO(String simulationName, int amountOfRunning, List<TerminateConditionDTO> terminateConditions) {
        this.simulationName = simulationName;
        this.amountOfRunning = amountOfRunning;
        this.terminateConditions = terminateConditions;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public int getAmountOfRunning() {
        return amountOfRunning;
    }

    public List<TerminateConditionDTO> getTerminateConditions() {
        return terminateConditions;
    }
}
