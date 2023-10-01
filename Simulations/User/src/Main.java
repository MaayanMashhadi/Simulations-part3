import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("screens/LoginScene.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Predictions");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch();
    }



}