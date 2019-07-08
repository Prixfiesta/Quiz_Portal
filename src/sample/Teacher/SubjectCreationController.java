package sample.Teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;

public class SubjectCreationController {

    @FXML
    private JFXTextField subjectname;

    @FXML
    private JFXTextField subjectcode;

    @FXML
    private JFXButton createsubject;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton back;

    public void initialize(){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        subjectname.getValidators().add(validator);
        subjectname.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                subjectname.validate();
            }
        });
        subjectcode.getValidators().add(validator);
        subjectcode.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                subjectcode.validate();
            }
        });

    }

    @FXML
    void backClicked(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void createSubject(ActionEvent event)throws IOException {
        Session_Id teacher = new Session_Id();
        Main.user.sendString("addSubjects");
        Main.user.sendString(subjectname.getText());
        Main.user.sendString(teacher.getUsername());
        Main.user.sendString(subjectcode.getText());
        boolean check = Main.user.recieveBoolean();
        System.out.println(check);
        if(check){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Subject Created");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml"));
            Scene loginscene = new Scene(root);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(loginscene);
            window.show();
        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Subject Creation Failed");
            alert.showAndWait();
        }
    }

}
