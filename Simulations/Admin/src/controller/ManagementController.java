package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.xml.bind.JAXBException;
import java.util.concurrent.TimeUnit;

public class ManagementController {
    @FXML private TextField filePathTextField;
    private final StringProperty loadedFilePath = new SimpleStringProperty();
    public void initialize() {
        filePathTextField.textProperty().bind(loadedFilePath);

        loadedFilePath.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {

            }
        });



    }
}
