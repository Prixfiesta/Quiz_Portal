package sample.Student;

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
import java.util.ArrayList;
import java.util.List;

public class LoadTestController {

    private Session_Id current = new Session_Id();

    @FXML
    private JFXButton starttest;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField test_id;

    public void initialize(){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        test_id.getValidators().add(validator);
        test_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                test_id.validate();
            }
        });

    }

    @FXML
    void backClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StudentDashboard.fxml"));
        Scene dash = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(dash);
        window.show();

    }

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void startTest(ActionEvent event) throws IOException {
        Main.user.sendString("getSections");
        Main.user.sendString(test_id.getText());
        current.setTest_id(test_id.getText());
        List<String> sections = (ArrayList<String>)(Main.user.recieveObject());
        if(sections.size()==0){
            System.out.println("dada");
        }
        else
        System.out.println(sections.get(0));
        String [] sectionsar = sections.toArray(new String[sections.size()]);
        // toArray(none) return Object(Object[]) which has to be typecasted
        // toArray(type[]) returns Array of type[]
        current.setSections(sectionsar);
        current.setNo_of_sections(sectionsar.length);
        current.setMarks(0);

        if(sectionsar.length!=0){
            Parent root = FXMLLoader.load(getClass().getResource("SectionLaunch.fxml"));
            Scene dash = new Scene(root);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(dash);
            window.show();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Test Id is incorrect");
        }

    }

}
