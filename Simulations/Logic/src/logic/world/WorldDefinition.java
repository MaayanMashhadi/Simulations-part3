package logic.world;

import logic.definition.entity.api.EntityDefinition;
import logic.definition.environment.api.EnvVariablesManger;
import logic.definition.environment.impl.EnvVariableManagerImpl;
import logic.definition.property.api.PropertyDefinition;
import logic.rule.Rule;
import logic.terminateCondition.TerminateCondition;

import java.util.*;

public class WorldDefinition {
    private List<EntityDefinition> population;
    private int ticks;
    private int numberOfThreads;
    private int rows;
    private int columns;

    private List<TerminateCondition> terminateConditions;

    private static WorldDefinition instance;

    private EnvVariablesManger environmentsVariables = new EnvVariableManagerImpl();

    private List<Rule> rules;

    public WorldDefinition(){
        ticks = 0;
        population = new ArrayList<>();
        this.terminateConditions = new ArrayList<>();
        rules = new ArrayList<>();

    }
    public WorldDefinition(int rows, int columns){
        this();
        this.rows = rows;
        this.columns = columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setEnvironmentsVariables(EnvVariablesManger envVariablesManger){
        this.environmentsVariables = envVariablesManger;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getTicks() {
        return ticks;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Collection<EntityDefinition> getPopulation() {
        return population;
    }

    public  void addEntity(EntityDefinition entity){
        population.add(entity);
    }

    public void addTerminateCondition(TerminateCondition terminateCondition){
        terminateConditions.add(terminateCondition);
    }

    public void addRule(Rule rule){
        rules.add(rule);
    }

    public static WorldDefinition getInstance() {
        if (instance == null) {
            instance = new WorldDefinition();
        }
        return instance;
    }

    public PropertyDefinition findEnvironmentsVariables(String propertyName) throws NoSuchElementException {
        for(PropertyDefinition p : environmentsVariables.getEnvVariables()){
            if(p.getName().equals(propertyName)){
                return p;
            }
        }
        throw new NoSuchElementException("The environment variable name " +
                propertyName + " doesn't exists");
    }

    public List<TerminateCondition> getTerminateConditions() {
        return terminateConditions;
    }

    public EnvVariablesManger getEnvironmentsVariables(){
        return environmentsVariables;
    }

    public void searchForZeroEntity(){
        population.removeIf(entity -> entity.getStartPopulation() == 0);
    }

    public EntityDefinition getEntityByName(String entityName) {
        for(EntityDefinition entityDefinition : population){
            if(entityDefinition.getName().equals(entityName)){
                return entityDefinition;
            }
        }
        throw new NoSuchElementException("The entity name " + entityName + " doesn't exist");
    }
}
