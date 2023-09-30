package dto;

import java.util.List;

public class EntityDefinitionDTO {
    private final String name;
    private final List<PropertyDefinitionDTO> properties;
    private  int startPopulation;
    private final int endPopulation;

    public EntityDefinitionDTO(String name, List<PropertyDefinitionDTO> propertyDefinitionDTOS, int startPopulation
    , int endPopulation){
        this.name = name;
        properties = propertyDefinitionDTOS;
        this.startPopulation = startPopulation;
        this.endPopulation = endPopulation;
    }

    public void setStartPopulation(int startPopulation) {
        this.startPopulation = startPopulation;
    }

    public int getEndPopulation() {
        return endPopulation;
    }

    public int getStartPopulation() {
        return startPopulation;
    }

    public List<PropertyDefinitionDTO> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }
}
