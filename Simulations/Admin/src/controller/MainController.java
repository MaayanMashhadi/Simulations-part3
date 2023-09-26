package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainController {
    @FXML private Button ManagementButton;
    @FXML private HBox hboxScene;

    public void initialize() {

    }

    @FXML
    private void setOnActionAllocationsButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/AllocationsScene.fxml"));
            Parent newSceneRoot = fxmlLoader.load();
            AllocationController allocationController = fxmlLoader.getController();
            hboxScene.getChildren().clear();
            hboxScene.getChildren().add(newSceneRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    private void setOnActionManagementButton(){
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ManagmentScene.fxml"));
                Parent newSceneRoot = fxmlLoader.load();
                ManagementController managementController = fxmlLoader.getController();

                hboxScene.getChildren().clear();
                hboxScene.getChildren().add(newSceneRoot);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
