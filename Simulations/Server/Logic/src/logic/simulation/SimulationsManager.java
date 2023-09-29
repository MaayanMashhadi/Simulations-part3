package logic.simulation;

import logic.definition.entity.api.EntityDefinition;
import logic.dto.ActiveEnvironmentDTO;
import logic.dto.DTOCreator;
import logic.dto.SimulationDTO;
import logic.dto.SimulationHistory;
import logic.execution.instance.environment.api.ActiveEnvironment;
import logic.world.WorldDefinition;
import logic.world.WorldInstance;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SimulationsManager {

    private int id = 1;
    private List<Simulation> simulationList;
    private ExecutorService threadPool;
    private DTOCreator dtoCreator;
    public void createThreadPool(int numberOfThread){
        threadPool = Executors.newFixedThreadPool(numberOfThread);
    }
    public SimulationsManager(){
        dtoCreator = new DTOCreator();
    }
    public void addSimulation(Simulation simulation) {
        if (simulationList == null) {
            simulationList = new ArrayList<>();
        }
        simulation.setId(id);
        simulation.setDate(new Date());
        simulationList.add(simulation);
        id++;
    }

    public List<Simulation> getSimulationList(){
        return simulationList;
    }

    public Simulation getSimulationById(int id){
        for(Simulation simulation : simulationList){
            if(simulation.getId() == id){
                return simulation;
            }
        }
        return null;
    }

    public SimulationDTO startSimulation(ActiveEnvironment activeEnvironment, WorldDefinition worldDefinition,
                                         SimulationHistory simulationHistory){
       WorldInstance worldInstance = new WorldInstance(worldDefinition, activeEnvironment,
               simulationHistory);

        Simulation simulation = new Simulation(worldInstance,worldDefinition);
        simulation.setSimulationHistory(simulationHistory);
        this.addSimulation(simulation);
        threadPool.execute(simulation);
        SimulationDTO simulationDTO1 = dtoCreator.createSimulationDTO(worldDefinition,simulation, simulation.getSimulationOutput());
        return simulationDTO1;
    }
    public void pauseSimulation(int id){
        for(Simulation simulation : simulationList){
            if(simulation.getId() == id){
               simulation.pauseSimulation();
                    simulation.setPause(true);

            }
        }

    }
    public void resumeSimulation(int id){
        for(Simulation simulation : simulationList){
            if(simulation.getId() == id){
                simulation.resumeSimulation();
                    simulation.setPause(false);

            }
        }
    }
    public void stopSimulation(int id){
        for(Simulation simulation : simulationList){
            if(simulation.getId() == id){
                simulation.stopSimulation();
                    simulation.setEndSimulation(true);

            }
        }
    }
    public ThreadPoolExecutor getThreadPool() {

        return (ThreadPoolExecutor) threadPool;
    }


}

