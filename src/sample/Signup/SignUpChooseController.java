package sample.Signup;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class SignUpChooseController {

    @FXML
    private JFXButton teachersignup;

    @FXML
    private JFXButton studentsignup;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton Login;

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void studentSignUpClicked(ActionEvent event)throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("StudentSignUp.fxml"));
        Scene signupscene = new Scene(signup);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Student Signup");
        window.setScene(signupscene);
        window.show();
    }

    @FXML
    void teacherSignupClicked(ActionEvent event) throws IOException{
        Parent signup = FXMLLoader.load(getClass().getResource("TeacherSignUp.fxml"));
        Scene signupscene = new Scene(signup);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Teacher Signup");
        window.setScene(signupscene);
        window.show();
    }

    @FXML
    void toLoginPage(MouseEvent event)throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }

}
