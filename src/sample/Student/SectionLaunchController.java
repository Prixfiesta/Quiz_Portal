package sample.Student;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Session_Id;

import java.io.IOException;

public class SectionLaunchController {

    private int section_no = 0;
    private Session_Id current = new Session_Id();
    private String [] sections = current.getSections();

    @FXML
    private Label test_id;

    @FXML
    private JFXButton sectionbutton;

    public void initialize(){
        test_id.setText(current.getTest_id());
        sectionbutton.setText("Open Section 1");
    }
    // Couldn't use initialize to open first section because you can't get curr Window without an event
    @FXML
    void iter(ActionEvent event) throws IOException {
        if(section_no<current.getNo_of_sections()) {


            Stage stage = new Stage();
            System.out.println("Total no of sections is "+current.getNo_of_sections());
            System.out.println("Current section no: "+section_no);
            Session_Id.setSection_id(sections[section_no]);
            Parent root = FXMLLoader.load(getClass().getResource("AttemptSection.fxml"));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
            section_no++;
            sectionbutton.setText("Open Section " + (section_no + 1));
            if(section_no==current.getNo_of_sections()){
                sectionbutton.setText("Finish");
            }
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("ShowResult.fxml"));
            Scene loginscene = new Scene(root);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(loginscene);
            window.show();
        }
    }


}
