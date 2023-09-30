package controller;

import com.google.gson.Gson;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;


public class MainController {
    @FXML private Button ManagementButton;
    @FXML private HBox hboxScene;
    private Facade facade;
    public final static String BASE_URL = "http://localhost:8080";
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public void initialize() {

    }
    private void requestFacade(){
        String RESOURCE = "/Server_Web_exploded/queue-management";
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    facade = gson.fromJson(jsonResponse, Facade.class);
                }
                else {
                    // Handle unsuccessful response here
                    System.err.println("HTTP Error: " + response.code());
                }
            }
        });

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
            //requestFacade();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ManagmentScene.fxml"));
            Parent newSceneRoot = fxmlLoader.load();
            ManagementController managementController = fxmlLoader.getController();
            //managementController.setFacade(facade);

            hboxScene.getChildren().clear();
            hboxScene.getChildren().add(newSceneRoot);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
