package utils.user;

import dto.RequestDetailsDTO;
import dto.WorldDefinitionDTO;

import java.util.ArrayList;
import java.util.List;

public class RequestsManager {
    List<RequestDetailsDTO> allRequests;

    public RequestsManager(){
        allRequests = new ArrayList<>();
    }

    public void addRequest(RequestDetailsDTO requestDetailsDTO) {

        allRequests.add(requestDetailsDTO);
    }

    public List<RequestDetailsDTO> getAllRequests() {
        return allRequests;
    }
    public RequestDetailsDTO getRequestByName(String name){
        return allRequests.stream()
                .filter(requestDetailsDTO -> requestDetailsDTO.getSimulationName().equals(name))
                .findFirst().orElse(null);
    }
    public void removeSimulationByName(String name){
        allRequests.removeIf(requestDetailsDTO -> requestDetailsDTO.getSimulationName().equals(name));
    }
}
