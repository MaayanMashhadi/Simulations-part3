package dto;


import java.util.Date;

public class SimulationDTO {
    private final WorldDefinitionDTO worldDefinitionDTO;
    private boolean isWaitingSimulation;

    private final int id;
    private final  Date date;
    private final SimulationOutputDTO simulationOutput;
    private boolean endSimualtion;
    private boolean isRUnning = false;
    private SimulationCurrentDetailsDTO currentDetailsDTO;

    public void setCurrentDetailsDTO(SimulationCurrentDetailsDTO currentDetailsDTO) {
        this.currentDetailsDTO = currentDetailsDTO;
    }

    public SimulationCurrentDetailsDTO getCurrentDetailsDTO() {
        return currentDetailsDTO;
    }

    public SimulationDTO(int id, Date date, SimulationOutputDTO simulationOutput,
                         WorldDefinitionDTO worldInstanceDTO) {
        this.id = id;
        this.date = date;
        this.simulationOutput = simulationOutput;
        this.worldDefinitionDTO = worldInstanceDTO;

    }

    public void setWaitingSimulation(boolean waitingSimulation) {
        isWaitingSimulation = waitingSimulation;
    }

    public boolean isWaitingSimulation() {
        return isWaitingSimulation;
    }

    public WorldDefinitionDTO getWorldDefinitionDTO() {
        return worldDefinitionDTO;
    }

    public SimulationOutputDTO getSimulationOutput() {
        return simulationOutput;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public boolean getEndSimualtion(){
        return endSimualtion;
    }
    public void setEndSimualtion(boolean endSimualtion){
        this.endSimualtion = endSimualtion;
    }

    public boolean isRunning() {
        return isRUnning;
    }
    public void setRunning(boolean isRUnning){
        this.isRUnning = isRUnning;
    }
}
