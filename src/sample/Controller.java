package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class Controller {
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton signupbutton;

    @FXML
    private JFXTextField user_id;

    @FXML
    private JFXRadioButton asteacher;

    @FXML
    private JFXRadioButton asstudent;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton close;

    @FXML
    private void initialize(){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("User_id is Required");
        user_id.getValidators().add(validator);
        user_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                user_id.validate();
            }
        });
    }

    @FXML
    void loginClicked(ActionEvent event) throws SQLException, InvalidKeySpecException, IOException {
        Main.user.sendString("Login");
        Session_Id current = new Session_Id();
        if(asstudent.isSelected()&&asteacher.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select only one of Student or Teacher");
            alert.showAndWait();
        }
        else if(asstudent.isSelected()){
            Main.user.sendString("Student");
            Main.user.sendString(user_id.getText());
            Main.user.sendString(password.getText());
            boolean login = Main.user.recieveBoolean();
            if(login){
                current.setUsername(user_id.getText());
                Parent dashBoard = FXMLLoader.load(getClass().getResource("Student/StudentDashBoard.fxml"));
                Scene dashboardscene = new Scene(dashBoard);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Student Dashboard");
                window.setScene(dashboardscene);
                window.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Login And Password is wrong");
                alert.showAndWait();

            }
        }
        else if(asteacher.isSelected()){
            Main.user.sendString("Teacher");
            Main.user.sendString(user_id.getText());
            Main.user.sendString(password.getText());
            boolean login = Main.user.recieveBoolean();
            if(login){
                current.setUsername(user_id.getText());
                System.out.println("Teacher has logged in");
                Parent dashBoard = FXMLLoader.load(getClass().getResource("Teacher/TeacherDashBoard.fxml"));
                Scene dashboardscene = new Scene(dashBoard);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Teacher Dashboard");
                window.setScene(dashboardscene);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Login And Password is wrong");
                alert.showAndWait();

            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please choose between Teacher and Student");
            alert.showAndWait();

        }
    }

    @FXML
    void closeButtonClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void onSignupClicked(ActionEvent event) throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("Signup/SignUpChoose.fxml"));
        Scene signupscene = new Scene(signup);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Choose Profile");
        window.setScene(signupscene);
        window.show();

    }
}
