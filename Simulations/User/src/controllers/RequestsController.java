package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.*;

import java.io.IOException;

import static controllers.LoginController.BASE_URL;
import static controllers.LoginController.HTTP_CLIENT;

public class RequestsController {
    @FXML private TextField amountRunningTextField;
    @FXML private TextField simulationNameTextfied;
    @FXML private Button submitButton;
    @FXML private RadioButton byUserRadioChoice;
    @FXML private RadioButton byTicksRadioChoice;
    @FXML private Label radioChoiceLabel;

    public void initialize(){
        ToggleGroup toggleGroup = new ToggleGroup();
        byUserRadioChoice.setToggleGroup(toggleGroup);
        byTicksRadioChoice.setToggleGroup(toggleGroup);
    }

    @FXML
    private void handleRadioButtonClick(ActionEvent event) {
        radioChoiceLabel.setText("");
        if (byUserRadioChoice.isSelected()) {
            radioChoiceLabel.setText("by user selected");
        }
        else if (byTicksRadioChoice.isSelected()) {
            radioChoiceLabel.setText("by ticks/seconds selected");

        }
    }

    @FXML private void onSubmitButton(){
        String RESOURCE = "/Server_Web_exploded/request-process-servlet";
        String simulationName = simulationNameTextfied.getText();
        String amountOfRunning = amountRunningTextField.getText();
        RequestBody requestBody = new FormBody.Builder()
                .add("simulationName", simulationName)
                .add("amountOfRunning", amountOfRunning)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(requestBody)
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }


}
