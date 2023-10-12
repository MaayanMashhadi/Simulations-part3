package dto;

import logic.definition.entity.api.EntityDefinition;

import java.util.List;

public class SimulationHistoryDTO {
    private List<PropertyInstanceDTO> propertyInstanceDTOS;
    private List<EntityDefinitionDTO> entityDefinitionsDTOS;
    private ActiveEnvironmentDTO activeEnvironmentDTO;

    public SimulationHistoryDTO(ActiveEnvironmentDTO activeEnvironmentDTO,
                                List<PropertyInstanceDTO> propertyInstanceDTOS, List<EntityDefinitionDTO> entityDefinitionsDTOS) {
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

    public List<EntityDefinitionDTO> getEntityDefinitionsDTOS() {
        return entityDefinitionsDTOS;
    }
}
