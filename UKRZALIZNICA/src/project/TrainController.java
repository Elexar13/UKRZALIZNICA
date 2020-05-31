package project;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TrainController{

    @FXML private GridPane gridpane;
    @FXML private ColumnConstraints grifPane;
    @FXML private Label place1;
    @FXML private Label place2;
    @FXML private Label place4;
    @FXML private Label place3;
    @FXML private Label place53;
    @FXML private Label place54;
    @FXML private Label place6;
    @FXML private Label place5;
    @FXML private Label place8;
    @FXML private Label place7;
    @FXML private Label place51;
    @FXML private Label place52;
    @FXML private Label place10;
    @FXML private Label place9;
    @FXML private Label place12;
    @FXML private Label place11;
    @FXML private Label place49;
    @FXML private Label place50;
    @FXML private Label place13;
    @FXML private Label place14;
    @FXML private Label place16;
    @FXML private Label place15;
    @FXML private Label place47;
    @FXML private Label place48;
    @FXML private Label place18;
    @FXML private Label place17;
    @FXML private Label place19;
    @FXML private Label place20;
    @FXML private Label place45;
    @FXML private Label place46;
    @FXML private Label place21;
    @FXML private Label place22;
    @FXML private Label place23;
    @FXML private Label place24;
    @FXML private Label place43;
    @FXML private Label place44;
    @FXML private Label place25;
    @FXML private Label place26;
    @FXML private Label place28;
    @FXML private Label place27;
    @FXML private Label place41;
    @FXML private Label place42;
    @FXML private Label place30;
    @FXML private Label place29;
    @FXML private Label place32;
    @FXML private Label place31;
    @FXML private Label place39;
    @FXML private Label place40;
    @FXML private Label place34;
    @FXML private Label place33;
    @FXML private Label place36;
    @FXML private Label place35;
    @FXML private Label place37;
    @FXML private Label place38;
    @FXML private Label infoLabel;
    @FXML private Button buyButton;
    @FXML private Label infolabel;

    HashMap<Label, Integer> labels;
    int idreis = 0;
    String data[];
    static String places;
    @FXML
    void initialize() {
        labels = new HashMap<>();

        for (Object label : gridpane.getChildren()){
            labels.put((Label)label, 0);
        }

        for (Label label : labels.keySet()){
            label.setOnMouseMoved(e->{
                if (labels.get(label)==0){
                    label.setStyle("-fx-border-color: #000000; -fx-background-color: #d4b268; -fx-background-radius: 4");
                }
            });
            label.setOnMouseExited(e->{
                if (labels.get(label)==0) {
                    label.setStyle("-fx-background-color: #d4b268;");
                }
            });
            label.setOnMouseClicked(e->{
                if (labels.get(label)==0){
                    label.setStyle("-fx-border-color: #808080;");
                    labels.put(label, 1);
                }else{
                    label.setStyle("-fx-border-color: #000000; -fx-background-color: #d4b268; -fx-background-radius: 4");
                    labels.put(label, 0);
                }
            });
        }

        buyButton.setOnAction(e->{
            buyTicket(e);
        });

        initTrain();
    }

    void initTrain(){
        data = ChooseCitiesController.ticketData.split(" ");
        idreis = Integer.parseInt(data[0]);
        PreparedStatement preparedStatement;
            try {
                preparedStatement = Main.connection.prepareStatement("SELECT place, status FROM train WHERE idreis = '"+idreis+"'");
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    for (Label label : labels.keySet()){
                        if (Integer.parseInt(label.getText())==resultSet.getInt(1) & resultSet.getBoolean(2)){
                            label.setStyle("-fx-background-color: #696969;");
                            labels.put(label, 2);
                            label.setDisable(true);
                        }
                    }
                }
                for (Label label : labels.keySet()){
                    if (labels.get(label)==2){
                        PreparedStatement preparedStatement2 = Main.connection.prepareStatement("select t.idcityto, rc.orderofcity from tickets t " +
                                "inner join reis_cities rc on t.idreis = rc.idreis " +
                                "where t.place = '"+label.getText()+"' and t.idreis = '"+idreis+"' and rc.idcity = t.idcityto");
                        ResultSet resultSet2 = preparedStatement2.executeQuery();
                        while(resultSet2.next()){
                            PreparedStatement preparedStatement3 = Main.connection.prepareStatement("select rc.orderofcity from reis_cities rc " +
                                    "inner join cities c on rc.idcity = c.idcity " +
                                    "where rc.idcity = (select idcity from cities where cityname = '"+ChooseCitiesController.cities.split(" ")[0]+"')");
                            ResultSet resultSet3 = preparedStatement3.executeQuery();
                            while(resultSet3.next()){
                                if (resultSet3.getInt(1)>=resultSet2.getInt(2)){
                                    label.setStyle("-fx-background-color: #d4b268; -fx-background-radius: 4");
                                    label.setDisable(false);
                                    labels.put(label, 0);
                                }
                            }
                        }
                        ArrayList<Integer> orderList = new ArrayList<>();
                        PreparedStatement preparedStatement4 = Main.connection.prepareStatement("select t.idcityfrom, rc.orderofcity from tickets t " +
                                "inner join reis_cities rc on t.idreis = rc.idreis " +
                                "where t.place = '"+label.getText()+"' and t.idreis = '"+idreis+"' and rc.idcity = t.idcityfrom");
                        ResultSet resultSet4 = preparedStatement4.executeQuery();
                        while(resultSet4.next()){
                            System.out.println("------"+resultSet4.getInt(1)+" - "+resultSet4.getInt(2));
                            orderList.add(resultSet4.getInt(2));
                        }
                        PreparedStatement preparedStatement5 = Main.connection.prepareStatement("select rc.orderofcity from reis_cities rc " +
                                "inner join cities c on rc.idcity = c.idcity " +
                                "where rc.idcity = (select idcity from cities where cityname = '"+ChooseCitiesController.cities.split(" ")[1]+"')");
                        ResultSet resultSet5 = preparedStatement5.executeQuery();
                        while (resultSet5.next()){
                            if (resultSet5.getInt(1)<=orderList.stream().mapToInt(v -> v).min().getAsInt()){
                                    label.setStyle("-fx-background-color: #d4b268; -fx-background-radius: 4");
                                    label.setDisable(false);
                                    labels.put(label, 0);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void buyTicket(ActionEvent event){
        places = null;
        for(Label label : labels.keySet()){
            if (labels.get(label)==1) {
                places += label.getText()+" ";
                }
            }
        for(Label label : labels.keySet()){
            if (labels.get(label)==1){
                Parent registrationParent = null;
                try {
                    registrationParent = FXMLLoader.load(getClass().getResource("dataForTickets.fxml"));
                    Scene registrationScene = new Scene(registrationParent);
                    Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                    window.setScene(registrationScene);
                    window.setResizable(false);
                    window.show();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void back(javafx.event.ActionEvent event) throws IOException {
        Parent registrationParent = FXMLLoader.load(getClass().getResource("chooseCities.fxml"));
        Scene registrationScene = new Scene(registrationParent);
        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScene);
        window.setResizable(false);
        window.show();
    }
}



