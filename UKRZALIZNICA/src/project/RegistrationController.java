package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    @FXML private TextField pibField;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;
    @FXML private Button cancellButton;
    @FXML private Label errorLabel;
   // public static User user;
    @FXML
    void initialize() {

    }

    public void registerUser(ActionEvent event){
        errorLabel.setText("");
        try{
            if (!pibField.getText().equals("") & !loginField.getText().equals("") & !passwordField.getText().equals("")){
                 Controller.user = new User(pibField.getText(), loginField.getText(), passwordField.getText());
                //int status = 0;
                //PreparedStatement preparedStatement = Main.connection.prepareStatement("INSERT INTO users (fio, login, password) values('"+pibField.getText()+"',  '"+loginField.getText()+"', '"+passwordField.getText()+"')");
                PreparedStatement preparedStatement = Main.connection.prepareStatement("call add_user('"+pibField.getText()+"',  " +
                        "'"+loginField.getText()+"', '"+passwordField.getText()+"', @status);");
                preparedStatement.executeUpdate();
                PreparedStatement preparedStatement2 = Main.connection.prepareStatement("SELECT @status");
                ResultSet resultSet = preparedStatement2.executeQuery();
                while(resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                    if (resultSet.getInt(1)==2){
                        try{
                            Parent registrationParent = FXMLLoader.load(getClass().getResource("profile.fxml"));
                            Scene registrationScene = new Scene(registrationParent);
                            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                            window.setScene(registrationScene);
                            window.setResizable(false);
                            window.show();
                        }catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }

            }else throw new NullPointerException();

        }catch (NullPointerException ex){
            errorLabel.setText("*всі поля повинні бути заповненні");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void toLoginScene(ActionEvent event) throws IOException {
        Parent registrationParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene registrationScene = new Scene(registrationParent);
        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScene);
        window.setResizable(false);
        window.show();
    }
}
