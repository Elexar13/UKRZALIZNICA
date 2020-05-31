package project;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class Controller {

    @FXML private Button loginButton;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button register;
    @FXML private Label errorLabel;
    public static User user;
    @FXML
    void initialize() {
       register.setOnMouseMoved(e->{
           register.setStyle("-fx-background-color: #d4b268");
       });
       register.setOnMouseExited(e->{
           register.setStyle("-fx-background-color: #f4f4f4");
       });

    }

       public void toRegisterScene(ActionEvent event) throws IOException {
        Parent registrationParent = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Scene registrationScene = new Scene(registrationParent);
        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScene);
        window.setResizable(false);
        window.show();
    }

    public void toProfileScene(ActionEvent event) throws IOException {
        errorLabel.setText("");
        try{
            if (!loginField.getText().equals("") & !passwordField.getText().equals("")){
                PreparedStatement preparedStatement = Main.connection.prepareStatement(
                        "call log_in('"+loginField.getText()+"', '"+passwordField.getText()+"', @status);");
                preparedStatement.executeUpdate();
                PreparedStatement preparedStatement2 = Main.connection.prepareStatement("SELECT @status");
                ResultSet resultSet = preparedStatement2.executeQuery();
                while(resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                    if (resultSet.getInt(1)==1){
                        PreparedStatement preparedStatement3 = Main.connection.prepareStatement("SELECT fio FROM users WHERE password = '"+passwordField.getText()+"'");
                        ResultSet resultSet2 = preparedStatement3.executeQuery();
                        while(resultSet2.next()) user = new User(resultSet2.getString(1), loginField.getText(), passwordField.getText());
                        Parent profileParent = FXMLLoader.load(getClass().getResource("profile.fxml"));
                        Scene profileScene = new Scene(profileParent);
                        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                        window.setScene(profileScene);
                        window.setResizable(false);
                        window.show();
                    }else errorLabel.setText("Такого користувача не існує");
                }
            }else throw new NullPointerException();
        }catch (NullPointerException ex){
            errorLabel.setText("*Всi поля повинні бути заповнені");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
