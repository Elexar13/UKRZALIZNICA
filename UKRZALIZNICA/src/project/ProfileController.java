package project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfileController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button myTicketsButton;
    @FXML private Button historyButton;
    @FXML private Button buyTicketButton;
    @FXML private Label infolabel;
    @FXML private Button personalDataButton;
    static Stage windowMyTickets;

    @FXML
    void initialize() {
        infolabel.setText(Controller.user.FIO);

    }

    public void toChooseCitiesScene(ActionEvent event) throws IOException {
        Parent chooseCitiesParent = FXMLLoader.load(getClass().getResource("chooseCities.fxml"));
        Scene chooseCitiesScene = new Scene(chooseCitiesParent);
        Stage window = new Stage();
        window.setScene(chooseCitiesScene);
        window.setResizable(false);
        window.show();
    }

    public void toMyTicketsScene(ActionEvent event) throws IOException {
        Parent chooseCitiesParent = FXMLLoader.load(getClass().getResource("myTicketsScene.fxml"));
        Scene chooseCitiesScene = new Scene(chooseCitiesParent);
        windowMyTickets = new Stage();
        windowMyTickets.setScene(chooseCitiesScene);
        windowMyTickets.setResizable(false);
        windowMyTickets.show();
    }
}
