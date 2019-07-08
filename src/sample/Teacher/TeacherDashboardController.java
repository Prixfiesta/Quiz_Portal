package sample.Teacher;

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

public class TeacherDashboardController {

    @FXML
    private Label welcomelabel;

    @FXML
    private JFXButton createsubject;

    @FXML
    private JFXButton testcreate;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton Login;

    private Session_Id current = new Session_Id();

    public void initialize(){
        Main.user.sendString("Name");
        Main.user.sendString("Teacher");
        Main.user.sendString(current.getUsername());
        String name =  Main.user.recieveString();
        welcomelabel.setText("Welcome,\n"+name);

    }

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void createSubject(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SubjectCreation.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

    @FXML
    void testCreate(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("TestCreation.fxml"));
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
