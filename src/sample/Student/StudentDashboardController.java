package sample.Student;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;

public class StudentDashboardController {

    private Session_Id current = new Session_Id();

    @FXML
    private Label welcomelabel;

    @FXML
    private JFXButton taketest;

    @FXML
    private JFXButton Login;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton seeresult;



    public void initialize(){
        Main.user.sendString("Name");
        Main.user.sendString("Student");
        Main.user.sendString(current.getUsername());
        String name =  Main.user.recieveString();
        welcomelabel.setText("Welcome,\n"+name);

    }

    @FXML
    void seeResult(ActionEvent event) {

    }

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void takeTest(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LoadTest.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

    @FXML
    void toLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

}
