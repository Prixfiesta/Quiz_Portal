package sample.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import java.util.List;

public class AttemptSectionController {

    private List<String[]> questions;

    private String[] answers;

    private Session_Id current  = new Session_Id();

    private int i =1;
    private int marks =0;
    @FXML
    private Label test_id;

    @FXML
    private Label section_no;

    @FXML
    private Label question;

    @FXML
    private JFXButton next;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXRadioButton optionA;

    @FXML
    private ToggleGroup Answers;

    @FXML
    private JFXRadioButton optionB;

    @FXML
    private JFXRadioButton optionC;

    @FXML
    private JFXRadioButton optionD;

    public void initialize(){
        test_id.setText(current.getTest_id());
        section_no.setText(Integer.toString(i));
        Main.user.sendString("getQuestions");
        Main.user.sendString(current.getSection_id());
        Main.user.sendString(current.getTest_id());
        questions = (List<String[]>)Main.user.recieveObject();
        System.out.println("qu:"+questions.size());
        answers = new String[questions.size()];
        for(int i=0;i<answers.length;i++){
            answers[i] = "";
        }
        current.setNo_of_questions(questions.size());
        question.setText(questions.get(0)[0]);
        optionA.setText(questions.get(0)[1]);
        optionB.setText(questions.get(0)[2]);
        optionC.setText(questions.get(0)[3]);
        optionD.setText(questions.get(0)[4]);
        back.setDisable(true);
        if(questions.size()==1){
            next.setDisable(true);
        }

    }

    @FXML
    void backClicked(ActionEvent event) {
        if(optionA.isSelected()){
            answers[i-1] = "A";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "B";
        }
        else if(optionC.isSelected()){
            answers[i-1] = "C";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "D";
        }
        else{
            answers[i-1] = "";
        }
        Answers.selectToggle(null);
        i--;

        if(i==1){
            back.setDisable(true);
        }
        else back.setDisable(false);
        next.setDisable(false);
        switch(answers[i-1]){
            case "A": Answers.selectToggle(optionA);
                        break;
            case "B": Answers.selectToggle(optionB);
                        break;
            case "C": Answers.selectToggle(optionC);
                        break;
            case "D": Answers.selectToggle(optionD);
                        break;
            default:
                Answers.selectToggle(null);
        }
        question.setText(questions.get(i-1)[0]);
        optionA.setText(questions.get(i-1)[1]);
        optionB.setText(questions.get(i-1)[2]);
        optionC.setText(questions.get(i-1)[3]);
        optionD.setText(questions.get(i-1)[4]);

    }

    @FXML
    void nextClicked(ActionEvent event) {
        if(optionA.isSelected()){
            answers[i-1] = "A";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "B";
        }
        else if(optionC.isSelected()){
            answers[i-1] = "C";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "D";
        }
        else{
            answers[i-1] = "";
        }
        Answers.selectToggle(null);
        i++;
        if(i==current.getNo_of_questions()){
            next.setDisable(true);
        }
        else{
            next.setDisable(false);
        }
        back.setDisable(false);
        System.out.println("current i:"+i);
        System.out.println(answers.length);
        switch(answers[i-1]){
            case "A": Answers.selectToggle(optionA);
                        break;
            case "B": Answers.selectToggle(optionB);
                        break;
            case "C": Answers.selectToggle(optionC);
                        break;
            case "D": Answers.selectToggle(optionD);
                        break;
            default:
                Answers.selectToggle(null);
        }
        question.setText(questions.get(i-1)[0]);
        optionA.setText(questions.get(i-1)[1]);
        optionB.setText(questions.get(i-1)[2]);
        optionC.setText(questions.get(i-1)[3]);
        optionD.setText(questions.get(i-1)[4]);

    }

    @FXML
    void submitClicked(ActionEvent event) {
        if(optionA.isSelected()){
            answers[i-1] = "A";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "B";
        }
        else if(optionC.isSelected()){
            answers[i-1] = "C";
        }
        else if(optionB.isSelected()){
            answers[i-1] = "D";
        }
        else{
            answers[i-1] = "";
        }
        for(int i=0;i<current.getNo_of_questions();i++){
            if(answers[i].equalsIgnoreCase(questions.get(i)[5]));
            marks++;
        }
        current.setMarks(current.getMarks()+marks);
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        stage.close();
    }

}
