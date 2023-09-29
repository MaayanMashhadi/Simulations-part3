package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import okhttp3.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class ManagementController {
    @FXML private TextField filePathTextField;
    @FXML private ListView simulationsDetailsListView;
    private final StringProperty loadedFilePath = new SimpleStringProperty();
    public final static String BASE_URL = "http://localhost:8080";
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public void initialize() {
        filePathTextField.textProperty().bind(loadedFilePath);

        loadedFilePath.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {

            }
        });



    }
    @FXML private void onLoadFileAction() throws IOException {
        String RESOURCE = "/Server_Web_exploded/upload-file";
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        String selectedFilePath = null;
        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            loadedFilePath.set(selectedFilePath);

        }

        File f = new File(selectedFilePath);

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);



        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure here
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to upload file: " + e.getMessage());
                    alert.showAndWait();
                    System.out.println("on failure " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // Handle unsuccessful response here
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        try {
                            System.out.println(response.body().string());
                            alert.setContentText(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        alert.showAndWait();
                    });
                } else {
                    // Handle successful response here
                    final String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("File Upload Response");
                        alert.setHeaderText(null);
                        alert.setContentText(responseBody);
                        alert.showAndWait();
                    });
                }
            }
        });

    }
}
