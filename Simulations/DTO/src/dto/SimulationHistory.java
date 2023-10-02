package dto;

import logic.definition.entity.api.EntityDefinition;

import java.util.List;

public class SimulationHistory {
    private List<PropertyInstanceDTO> propertyInstanceDTOS;
    private List<EntityDefinition> entityDefinitionsDTOS;
    private ActiveEnvironmentDTO activeEnvironmentDTO;

    public SimulationHistory(ActiveEnvironmentDTO activeEnvironmentDTO, List<PropertyInstanceDTO> propertyInstanceDTOS, List<EntityDefinition> entityDefinitionsDTOS) {
        this.propertyInstanceDTOS = propertyInstanceDTOS;
        this.entityDefinitionsDTOS = entityDefinitionsDTOS;
        this.activeEnvironmentDTO = activeEnvironmentDTO;
    }

    public ActiveEnvironmentDTO getActiveEnvironmentDTO() {
        return activeEnvironmentDTO;
    }

    public List<PropertyInstanceDTO> getPropertyInstanceDTOS() {
        return propertyInstanceDTOS;
    }

    public List<EntityDefinition> getEntityDefinitionsDTOS() {
        return entityDefinitionsDTOS;
    }
}
