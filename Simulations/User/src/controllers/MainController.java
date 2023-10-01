package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainController {
    @FXML private Button simulationsDeatilsButton;
    @FXML private Button requestsButton;
    @FXML private Button executionButton;
    @FXML private Button resultsButton;
    @FXML private HBox hboxScene;

    @FXML public void setSimulationsDeatilsButtonOnAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/DetailsScene.fxml"));
            Parent newSceneRoot = fxmlLoader.load();
            SimulationsDetailsController managementController = fxmlLoader.getController();

            hboxScene.getChildren().clear();
            hboxScene.getChildren().add(newSceneRoot);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML public void setOnActionRequestsButton() {


    }
}
