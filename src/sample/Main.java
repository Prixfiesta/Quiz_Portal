package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Server_Client.Client;

public class Main extends Application {
public static Client user ;
    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new Client("127.0.0.1",6000);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {


        launch(args);
    }
}
