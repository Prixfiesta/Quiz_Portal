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
import sample.Session_Id;

import java.io.IOException;

public class ShowResultController {

    private Session_Id  current = new Session_Id();

    @FXML
    private Label marksscored;

    @FXML
    private Label totalmarks;

    @FXML
    private JFXButton back;

    @FXML
    private Label test_id;

    public void initialize(){
        marksscored.setText(Integer.toString(current.getMarks()));
        totalmarks.setText(Integer.toString(current.getNo_of_questions()));
        test_id.setText(current.getTest_id());
        int a[] = Session_Id.getMarksgainedpersection();
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }

    @FXML
    void toLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StudentDashboard.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

}
