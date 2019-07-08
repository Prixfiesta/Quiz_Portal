package sample.Signup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Main;
import sample.PasswordUtils;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

public class TeacherSignUpController {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confpass;

    @FXML
    private JFXButton submitbutton;

    @FXML
    private JFXButton Login;

    @FXML
    private JFXButton close;

    @FXML
    void closeClicked(ActionEvent event) {
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    void submitClicked(ActionEvent event) throws IOException, InvalidKeySpecException {
        String pass = password.getText();
        String confirmpass = confpass.getText();
        if(pass.equals(confirmpass)){
            PasswordUtils passutil = new PasswordUtils();
            String salt = passutil.getSalt(30);
            String securepass = passutil.generateSecurePassword(pass,salt);
            Main.user.sendString("Signup");
            Main.user.sendString("Teacher");
            Main.user.sendString(name.getText());
            Main.user.sendString(email.getText());
            Main.user.sendString(id.getText());
            Main.user.sendString(securepass);
            Main.user.sendString(salt);
            boolean signupcomplete = Main.user.recieveBoolean();
            if(signupcomplete){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have been registered");
                alert.showAndWait();
                Parent home_parent= FXMLLoader.load(getClass().getResource("../sample.fxml"));
                Scene Home= new Scene(home_parent);

                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(Home);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Passwords Do not match");
            alert.showAndWait();
        }
    }

    @FXML
    void toLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene loginscene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginscene);
        window.show();
    }
}
