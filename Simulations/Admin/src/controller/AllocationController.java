package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.RequestDetailsDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static controller.MainController.HTTP_CLIENT;

public class AllocationController {
    private ObservableList<RequestDetailsDTO> dataListTable;
    private Map<String, List<RequestDetailsDTO>> responseMap;
    @FXML
    private TableView requestTable;
    public void initialize(){
        ScheduledExecutorService updateRequestsTable = Executors.newSingleThreadScheduledExecutor();
        dataListTable = FXCollections.observableArrayList();
        updateRequestsTable.scheduleAtFixedRate(() -> {
            requestAllRequests();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void requestAllRequests(){
        String RESOURCE = "/Server_Web_exploded/requests-users";
        Request request = new Request.Builder()
                .url(MainController.BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = MainController.HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    Type mapType = new TypeToken<Map<String, List<RequestDetailsDTO>>>() {}.getType();
                     responseMap = gson.fromJson(jsonResponse, mapType);
                    if(responseMap != null){
                        for(List<RequestDetailsDTO> listRequestDetailsDTO : responseMap.values()){
                            for(RequestDetailsDTO requestDetailsDTO : listRequestDetailsDTO){
                                boolean isFound = false;
                                for(RequestDetailsDTO existingData : dataListTable){
                                    if(requestDetailsDTO.getRequestNumber() == existingData.getRequestNumber()){
                                        //TODO : need to add the still running and ending
                                        isFound = true;
                                        break;
                                    }
                                }
                                if(!isFound){
                                    dataListTable.add(new RequestDetailsDTO(requestDetailsDTO.getUserName(),requestDetailsDTO.getRequestNumber(),
                                            requestDetailsDTO.getSimulationName(), requestDetailsDTO.getAmountOfRunning(),
                                            requestDetailsDTO.getRequestStatus(), requestDetailsDTO.getAmountOfSimulationsRuunning(),
                                            requestDetailsDTO.getAmountOfSimulationEnding(), requestDetailsDTO.getTerminateConditions()));
                                }
                            }
                            Platform.runLater(() ->{


                                requestTable.setItems(dataListTable);


                            });
                            }

                    }

                }
            }
        });

    }

}
