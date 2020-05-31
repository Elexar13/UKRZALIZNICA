package project;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyTicketsController {

    @FXML public ListView<String> listView;
    static boolean isOpen = false;
    static int place;
    public static Stage window;
    @FXML
    void initialize() {
        initList();
        listView.setOnMouseClicked(e -> {
           showTicket();
        });
    }

     public void initList(){
        listView.getItems().add("ID \t     |   Назва рейсу   \t |   Дата і час   \t  |   На ім'я     ");
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement("select distinct t.idticket, r.namereis, r.dateandtime, t.pib, u.iduser " +
                    "from tickets t inner join users_tickets u on t.idticket = u.idticket " +
                    "inner join reis r on t.idreis = r.idreis "+
                    "where u.iduser = (select iduser from users where fio = '"+Controller.user.FIO+"')");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listView.getItems().add(resultSet.getInt(1)+"\t\t"+resultSet.getString(2)+"\t\t"+resultSet.getDate(3)+"\t\t\t"+resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showTicket(){
        if (!isOpen){
            try {
                place = Integer.parseInt(listView.getSelectionModel().getSelectedItem().split("\t\t")[0]);
                Parent chooseCitiesParent = FXMLLoader.load(getClass().getResource("ticketView.fxml"));
                Scene chooseCitiesScene = new Scene(chooseCitiesParent);
                window = new Stage();
                window.setScene(chooseCitiesScene);
                window.setResizable(false);
                window.setOnCloseRequest(e ->{
                    isOpen=false;
                    listView.getItems().clear();
                    initList();
                    listView.refresh();
                });
                window.show();
                isOpen=true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
    }
}
