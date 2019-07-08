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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;
import java.sql.SQLException;

public class SectionCreationController {

    private int i=1;

    private int total_no_of_sections=0;
    private Session_Id current = new Session_Id();

    @FXML
    private Label section_no;

    @FXML
    private JFXTextField name_of_section;

    @FXML
    private JFXTextField no_of_questions;

    @FXML
    private JFXTextField time_for_section;

    @FXML
    private JFXButton createsection;

    public void initialize(){
        section_no.setText("Section no : 1");
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        name_of_section.getValidators().add(validator);
        name_of_section.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                name_of_section.validate();
            }
        });
        no_of_questions.getValidators().add(validator);
        no_of_questions.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                no_of_questions.validate();
            }
        });

        time_for_section.getValidators().add(validator);
        time_for_section.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                time_for_section.validate();
            }
        });
        total_no_of_sections = current.getNo_of_sections();

    }



    @FXML
    void createSections(ActionEvent event)throws IOException, SQLException {
        Main.user.sendString("createSection");
        Main.user.sendString(name_of_section.getText());
        Main.user.sendString(no_of_questions.getText());
        Main.user.sendString(time_for_section.getText());
        Main.user.sendString(current.getTest_id());
        Main.user.sendString(current.getTest_id()+"_"+i);
        boolean check = Main.user.recieveBoolean();
        System.out.println(i+"failurehitum");
        if(check){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Set questions please");
            alert.showAndWait();
            Session_Id.setNo_of_questions(Integer.parseInt(no_of_questions.getText()));
            Session_Id.setSection_id(current.getTest_id()+"_"+i);
            name_of_section.setText("");
            no_of_questions.setText("");
            time_for_section.setText("");
            i++;
            section_no.setText("Section No: " +i);
            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("QuestionCreation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add questions");
            stage.sizeToScene();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Section No "+i+" failed to be created");
            alert.showAndWait();

        }
        if(i==total_no_of_sections+1){
            Parent home_parent= FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.show();
        }
    }
}
