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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ChooseCitiesController {

    @FXML private ComboBox<String> fromBox;
    @FXML private ComboBox<String> toBox;
    @FXML private DatePicker date;
    @FXML private Label errorLabel;
    @FXML private ListView<String> listOfOffers;

    public static String ticketData;
    static String cities;
    static int payment;
    @FXML
    void initialize() {
        initChooseBox();
        listOfOffers.setOnMouseClicked(e->{
            ticketData = listOfOffers.getSelectionModel().getSelectedItem();
            try {
                cities = fromBox.getValue()+" "+toBox.getValue();
                Parent chooseCitiesParent = FXMLLoader.load(getClass().getResource("trainScene.fxml"));
                Scene chooseCitiesScene = new Scene(chooseCitiesParent);
                Stage window = (Stage)((javafx.scene.Node)e.getSource()).getScene().getWindow();
                window.close();
                window.setScene(chooseCitiesScene);
                window.setResizable(false);
                window.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    void initChooseBox(){
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement("SELECT cityname FROM cities");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                fromBox.getItems().add(resultSet.getString(1));
                toBox.getItems().add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void chooseCity(){
        errorLabel.setText("");
        if (fromBox.getValue() != null & toBox.getValue() != null & date.getValue()!=null){
            if (fromBox.getValue().equals(toBox.getValue())){
                errorLabel.setText("Неможливо обрати два однакових міста");
            }else{
                listOfOffers.getItems().clear();
                searchReis();
            }
        }
    }

    void searchReis(){
        try {
            int tempidreis = 0;
            int idreis = 0;
            int orderFirstCity = 0;
            int orderSecondCity = 0;
            PreparedStatement preparedStatement = Main.connection.prepareStatement("select rc.idreis, rc.idcity, " +
                    "c.cityname, rc.orderofcity, r.dateandtime " +
                    "from reis_cities rc " +
                    "inner join cities c on rc.idcity = c.idcity " +
                    "inner join reis r on rc.idreis = r.idreis " +
                    "order by idreis");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if (idreis!=resultSet.getInt(1)) {
                    tempidreis = 0;
                    orderFirstCity = 0;
                    orderSecondCity = 0;
                }
                if (resultSet.getString(3).equals(fromBox.getValue()) & date.getValue().isEqual(resultSet.getDate(5).toLocalDate()) & tempidreis==0){
                    tempidreis = resultSet.getInt(1);
                    orderFirstCity = resultSet.getInt(4);
                }if (resultSet.getString(3).equals(toBox.getValue()) & tempidreis==resultSet.getInt(1)){
                    orderSecondCity = resultSet.getInt(4);
                    PreparedStatement preparedStatement1 = Main.connection.prepareStatement("SELECT * from reis where idreis = '"+tempidreis+"'");
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while(resultSet1.next()){
                        payment = 35*(orderSecondCity-orderFirstCity);
                        listOfOffers.getItems().add(resultSet1.getInt(1)+" "+resultSet1.getString(2)+"\t"+resultSet1.getDate(3) +" " +
                                ""+resultSet1.getTime(3)+"\t"+payment+" грн");
                    }
                    tempidreis = 0;
                }
                idreis = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}