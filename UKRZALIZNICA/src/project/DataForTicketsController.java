package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataForTicketsController {

    @FXML private TextField nameField;
    @FXML private TextField lastnameField;
    @FXML private Label citiesLabel;
    @FXML private CheckBox studentCheckBox;
    @FXML private CheckBox pilgCheckBox;
    @FXML private Button buyButton;
    @FXML private Label reisLabel;
    @FXML private Label paymentLabel;
    @FXML private Label placeLabel;
    @FXML private AnchorPane tab;

    int counter = -1;
    float payment;
    @FXML
    void initialize() {
        initPane();
    }

    public void buyTicket(ActionEvent event){

        if (!nameField.getText().equals("") & !lastnameField.getText().equals("") & counter<TrainController.places.split(" ").length-1){
            inputInDB();
            initPane();
        }else if(!nameField.getText().equals("") & !lastnameField.getText().equals("") & counter>=TrainController.places.split(" ").length-1){
            if (TrainController.places.split(" ").length-1>=0) {
                inputInDB();
            }
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }

    void inputInDB(){
        try {
        PreparedStatement preparedStatement = Main.connection.prepareStatement("UPDATE train SET status = 1 WHERE place = '"+placeLabel.getText()+"' " +
                "AND idreis = '"+ChooseCitiesController.ticketData.split(" ")[0]+"'");
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement2 = Main.connection.prepareStatement("insert into tickets (idreis, pib, idcityfrom, idcityto, place) " +
                "values ('"+ChooseCitiesController.ticketData.split(" ")[0]+"', " +
                "'"+nameField.getText()+" "+lastnameField.getText()+"', " +
                "(SELECT idcity FROM cities WHERE cityname = '"+ChooseCitiesController.cities.split(" ")[0]+"'), " +
                "(SELECT idcity FROM cities WHERE cityname = '"+ChooseCitiesController.cities.split(" ")[1]+"'), " +
                "'"+placeLabel.getText()+"')");
        preparedStatement2.executeUpdate();
        PreparedStatement preparedStatement3 = Main.connection.prepareStatement("insert into users_tickets (iduser, idticket) " +
                "values((select iduser from users where fio = '"+Controller.user.FIO+"'), " +
                "(select idticket from tickets where idreis = '"+ChooseCitiesController.ticketData.split(" ")[0]+"' and " +
                "place = '"+placeLabel.getText()+"' and idcityto = (select idcity from cities where cityname = '"+ChooseCitiesController.cities.split(" ")[1]+"')))");
        preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void initPane(){
        studentCheckBox.setSelected(false);
        pilgCheckBox.setSelected(false);
        placeLabel.setText("");
        nameField.setText("");
        lastnameField.setText("");
            citiesLabel.setText(ChooseCitiesController.cities.split(" ")[0]+" - "+ChooseCitiesController.cities.split(" ")[1]);
            paymentLabel.setText("До оплати - "+ChooseCitiesController.payment+" грн");
            reisLabel.setText(ChooseCitiesController.ticketData.split(" ")[1]);
            try{
                if(counter<TrainController.places.split(" ").length-1){
                    counter++;
                    if (counter == 0) placeLabel.setText(TrainController.places.split(" ")[counter].substring(4));
                    else placeLabel.setText(TrainController.places.split(" ")[counter]);
                }
            }catch (Exception ex){
                counter = 0;
                ex.printStackTrace();
            }
    }

    public void changePayment(ActionEvent event){
        if (studentCheckBox.isSelected() | pilgCheckBox.isSelected()){
            payment = (float) (ChooseCitiesController.payment/1.5);
            paymentLabel.setText("До оплати - "+payment+" грн");
        }else{
            paymentLabel.setText("До оплати - "+ChooseCitiesController.payment+" грн");
        }
    }

    public void back(javafx.event.ActionEvent event) throws IOException {
        counter = 0;
        Parent registrationParent = FXMLLoader.load(getClass().getResource("trainScene.fxml"));
        Scene registrationScene = new Scene(registrationParent);
        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScene);
        window.setResizable(false);
        window.show();
    }
}
