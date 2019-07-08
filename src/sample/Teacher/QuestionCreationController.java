package sample.Teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

public class QuestionCreationController {

    @FXML
    private Label question_no;

    @FXML
    private JFXTextField question;

    @FXML
    private JFXTextField option_a;

    @FXML
    private JFXTextField option_b;

    @FXML
    private JFXTextField option_c;

    @FXML
    private JFXTextField option_d;

    @FXML
    private JFXComboBox<String> answer;

    @FXML
    private JFXButton createquestion;

    @FXML
    private Label sectionid;


    private int no_of_questions;
    private String test_id;
    private Session_Id current = new Session_Id();
    private int i=1;

    public void initialize(){
        question_no.setText("Question No : 1");
        no_of_questions = current.getNo_of_questions();
        sectionid.setText(current.getSection_id());
        RequiredFieldValidator validator = new RequiredFieldValidator();

        question.getValidators().add(validator);
        question.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                question.validate();
            }
        });

        answer.getItems().addAll("A","B","C","D");
    }

    @FXML
    void createQuestion(ActionEvent event) {
        Main.user.sendString("addQuestion");
        Main.user.sendString(current.getSection_id());
        Main.user.sendString(question.getText());
        Main.user.sendString(option_a.getText());
        Main.user.sendString(option_b.getText());
        Main.user.sendString(option_c.getText());
        Main.user.sendString(option_d.getText());
        Main.user.sendString(answer.getSelectionModel().getSelectedItem());
        Main.user.sendString(Integer.toString(i));
        Main.user.sendString(current.getTest_id());
        boolean check = Main.user.recieveBoolean();
        if(!check){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("question No "+i+" failed to be registered");
            alert.showAndWait();

        }
        i++;
        if(i==no_of_questions+1){
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.close();
        }
        question_no.setText("Question No :"+i);
        question.setText("");
        option_a.setText("");
        option_b.setText("");
        option_c.setText("");
        option_d.setText("");

    }

}
