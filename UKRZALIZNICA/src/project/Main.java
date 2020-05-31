package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    String userName = "root";
    String password = "1234";
    String connectionURL = "jdbc:mysql://localhost:3306/ukrzaliznica";
    public static Connection connection;
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, userName, password);
            System.out.println("Connection is successfully!");
        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("УКРЗАЛІЗНИЦЯ");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
