package controllers;

import com.google.gson.Gson;
import dto.RequestDetailsDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static controllers.LoginController.BASE_URL;
import static controllers.LoginController.HTTP_CLIENT;

public class RequestsController {
    @FXML private TextField amountRunningTextField;
    @FXML private TextField simulationNameTextfied;
    @FXML private Button submitButton;
    @FXML private RadioButton byUserRadioChoice;
    @FXML private RadioButton byTicksRadioChoice;
    @FXML private Label radioChoiceLabel;
    @FXML private TableView requestTable;
//    private TableColumn<RequestDetailsDTO, Integer> reqNumCol;
//    private TableColumn<RequestDetailsDTO, String> simNameCol;
//    private TableColumn<RequestDetailsDTO, Integer> amountOfRunningCol;
//    private TableColumn<RequestDetailsDTO, String> approvedCol;
//    private TableColumn<RequestDetailsDTO, Integer> stillRunningCol;
//    private TableColumn<RequestDetailsDTO, Integer> endingCol;
    private ObservableList<RequestDetailsDTO> dataListTable;
    private Integer choiceOfTicks = null;
    private Integer choiceOfSeconds = null;
    private Integer choiceOfUser = null;
    private RequestDetailsDTO[] requestsArray;
    private boolean isNotNull = false;

    public void initialize(){
//        reqNumCol = new TableColumn<>("Request's Number");
//        reqNumCol.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        ToggleGroup toggleGroup = new ToggleGroup();
        byUserRadioChoice.setToggleGroup(toggleGroup);
        byTicksRadioChoice.setToggleGroup(toggleGroup);
        ScheduledExecutorService updateRequestsTable = Executors.newSingleThreadScheduledExecutor();
        dataListTable = FXCollections.observableArrayList();

        updateRequestsTable.scheduleAtFixedRate(() -> {
            requestAllRequests();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void requestAllRequests(){
        String RESOURCE = "/Server_Web_exploded/all-requests";
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = HTTP_CLIENT.newCall(request);
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
                    requestsArray = gson.fromJson(jsonResponse, RequestDetailsDTO[].class);
                    if(requestsArray != null){
                        for(RequestDetailsDTO requestDetailsDTO : requestsArray){
                            boolean isFound = false;
                            for(RequestDetailsDTO existingData : dataListTable){
                                if(requestDetailsDTO.getRequestNumber() == existingData.getRequestNumber()){
                                    //TODO : need to add the still running and ending
                                    isFound = true;
                                    break;
                                }
                            }
                            if(!isFound){
                                dataListTable.add(new RequestDetailsDTO(requestDetailsDTO.getRequestNumber(),
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
        });

    }

    @FXML
    private void handleRadioButtonClick(ActionEvent event) {
        radioChoiceLabel.setText("");
        if (byUserRadioChoice.isSelected()) {
            radioChoiceLabel.setText("by user selected");
            choiceOfUser = 1;
        }
        else if (byTicksRadioChoice.isSelected()) {
            radioChoiceLabel.setText("by ticks/seconds selected");
            openDialogForTicksSeconds();

        }
    }
    private void openDialogForTicksSeconds(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Value");
        dialog.setHeaderText("If you want by ticks write 0, if you want by seconds write 1," +
                "if you want both write 2");
        dialog.setContentText("Value:");

        dialog.showAndWait().ifPresent(userChoice -> {
           Integer choice = processUserChoiceForAmountOfConditions(userChoice);
           if(choice == 0){
               TextInputDialog dialog1 = new TextInputDialog();
               dialog1.setTitle("Enter Value");
               dialog1.setHeaderText("Please enter ticks value");
               dialog1.setContentText("Value:");
               dialog1.showAndWait().ifPresent(userChoice1 -> {
                   choiceOfTicks = processUserChoice(userChoice1);
               });
           }
           else if(choice == 1){
               TextInputDialog dialog1 = new TextInputDialog();
               dialog1.setTitle("Enter Value");
               dialog1.setHeaderText("Please enter seconds value");
               dialog1.setContentText("Value:");
               dialog1.showAndWait().ifPresent(userChoice1 -> {
                   choiceOfSeconds = processUserChoice(userChoice1);
               });
           }
           else{
               TextInputDialog dialog1 = new TextInputDialog();
               dialog1.setTitle("Enter Value");
               dialog1.setHeaderText("Please enter ticks value");
               dialog1.setContentText("Value:");
               dialog1.showAndWait().ifPresent(userChoice1 -> {
                   choiceOfTicks = processUserChoice(userChoice1);
               });
               TextInputDialog dialog2 = new TextInputDialog();
               dialog2.setTitle("Enter Value");
               dialog2.setHeaderText("Please enter seconds value");
               dialog2.setContentText("Value:");
               dialog2.showAndWait().ifPresent(userChoice2 -> {
                   choiceOfSeconds = processUserChoice(userChoice2);
               });
           }
        });
    }
    private Integer processUserChoice(String userChoice){
        if (!integerCheck(userChoice)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The value you entered is incorrect. Please enter a DECIMAL number");
            alert.showAndWait();
            return null;

        }
        else{
            return Integer.parseInt(userChoice);
        }
    }
    private Integer processUserChoiceForAmountOfConditions(String userChoice){
        if (!integerCheck(userChoice) || !integerInRange(userChoice, 0, 2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The value you entered is incorrect. Please enter a DECIMAL number from 0 to 2");
            alert.showAndWait();
            return null;

        }
        else{
            return Integer.parseInt(userChoice);
        }
    }
    private static boolean integerInRange(String input, Integer from, Integer to) {
        try {
            int value = Integer.parseInt(input);
            return value >= (Integer)  from && value <= (Integer) to;



        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static boolean integerCheck(String input) {
        try {
            float integerValue = Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML private void onSubmitButton() {
        if (!simulationNameTextfied.getText().isEmpty() &&
                !amountRunningTextField.getText().isEmpty() && (choiceOfTicks != null || choiceOfSeconds != null || choiceOfUser != null)) {
           requestsRequest();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
    }

    private void requestsRequest(){
        String RESOURCE = "/Server_Web_exploded/request-process-user-servlet";
        String simulationName = simulationNameTextfied.getText();
        String amountOfRunning = amountRunningTextField.getText();
        RequestBody requestBody = new FormBody.Builder()
                .add("simulationName", simulationName)
                .add("amountOfRunning", amountOfRunning)
                .add("byUser", String.valueOf(choiceOfUser))
                .add("byTicks", String.valueOf(choiceOfTicks))
                .add("bySeconds", String.valueOf(choiceOfSeconds))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(requestBody)
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    requestsArray = gson.fromJson(jsonResponse, RequestDetailsDTO[].class);
                    for(RequestDetailsDTO requestDetailsDTO : requestsArray){
                        boolean isFound = false;
                        for(RequestDetailsDTO existingData : dataListTable){
                            if(requestDetailsDTO.getRequestNumber() == existingData.getRequestNumber()){
                                //TODO : need to add the still running and ending
                                isFound = true;
                                break;
                            }
                        }
                        if(!isFound){
                            dataListTable.add(new RequestDetailsDTO(requestDetailsDTO.getRequestNumber(),
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
        });

    }

    private void updateRequestsTable(){
//        if(!isNotNull){
            requestsRequest();
//        }
//        if (requestsArray != null) {
//            boolean isFound = false;
//            isNotNull = true;
//            for(RequestDetailsDTO requestDetailsDTO : requestsArray){
//                isFound = false;
//                for(RequestDetailsDTO existingData : dataListTable){
//                    if(requestDetailsDTO.getRequestNumber() == existingData.getRequestNumber()){
//                        //TODO : need to add the still running and ending
//                        isFound = true;
//                        break;
//                    }
//                }
//                if(!isFound){
//                    dataListTable.add(new RequestDetailsDTO(requestDetailsDTO.getRequestNumber(),
//                            requestDetailsDTO.getSimulationName(), requestDetailsDTO.getAmountOfRunning(),
//                            requestDetailsDTO.getRequestStatus(), requestDetailsDTO.getAmountOfSimulationsRuunning(),
//                            requestDetailsDTO.getAmountOfSimulationEnding(), requestDetailsDTO.getTerminateConditions()));
//                }
//            }
//            Platform.runLater(() ->{
//
//                requestTable.setItems(dataListTable);
//
//
//            });
//
//        }
    }

}
