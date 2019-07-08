package sample.Teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import javax.security.auth.Subject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCreationController{

    @FXML
    private JFXTextField name_of_test;

    @FXML
    private JFXTextField no_of_sections;

    @FXML
    private JFXComboBox<String> subjectselect;

    @FXML
    private JFXButton createtest;

    @FXML
    private JFXButton close;

//    @FXML
//    private Label user;

    @FXML
    private JFXButton back;

    private Session_Id current = new Session_Id();

    public void initialize(){

//        user.setText(current.getUsername());
//        Main.user.sendString("Name");
//        Main.user.sendString("Teacher");
//        Main.user.sendString(current.getUsername());
//        String name =  Main.user.recieveString();
//        user.setText(name);
        subjectselect.getItems().clear();
        Main.user.sendString("getSubjects");
        Main.user.sendString(current.getUsername());
        List<String> subjects = (ArrayList<String>)(Main.user.recieveObject());
        for(int i=0;i<subjects.size();i++){
            subjectselect.getItems().add(subjects.get(i));
        }

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        name_of_test.getValidators().add(validator);
        name_of_test.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                name_of_test.validate();
            }
        });
        no_of_sections.getValidators().add(validator);
        no_of_sections.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                no_of_sections.validate();
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
    void createTest(ActionEvent event) throws IOException{
        StringBuilder test_id = new StringBuilder();
        for(int i=0;i<4;i++){
            test_id.append("0123456789".charAt((int)(10*Math.random())));

        }
        Main.user.sendString("getSubjectId");
        Main.user.sendString(subjectselect.getSelectionModel().getSelectedItem());
        Main.user.sendString(current.getUsername());
        String subj_id = Main.user.recieveString();
        Session_Id.setTest_id("ID"+test_id.toString());
        Session_Id.setNo_of_sections(Integer.parseInt(no_of_sections.getText()));
        Main.user.sendString("createTest");
        Main.user.sendString(name_of_test.getText());
        Main.user.sendString("ID"+test_id.toString());
        Main.user.sendInt(Integer.parseInt(no_of_sections.getText()));
        Main.user.sendString(subj_id);
        Main.user.sendString(current.getUsername());
        boolean check = Main.user.recieveBoolean();
        if(check){
            Alert alert=  new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Test Created");
            alert.showAndWait();
            Parent home_parent= FXMLLoader.load(getClass().getResource("SectionCreation.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Test Creation Failed");
            alert.showAndWait();
        }

    }

}
