package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketViewController {

    @FXML private Label pib;
    @FXML private Label nameReis;
    @FXML private Label dateAndTime;
    @FXML private Label from;
    @FXML private Label to;
    @FXML private Label place;
    @FXML private Label id;
    @FXML private Label rid;
    @FXML private Button returnButton;

    int fromId, toId;
    @FXML
    void initialize() {
        initTicket();
    }

    void initTicket(){
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement("SELECT t.idticket, t.idreis, r.namereis, r.dateandtime, t.pib, t.place, t.idcityfrom, t.idcityto from " +
                    "tickets t inner join users_tickets ut on t.idticket = ut.idticket " +
                    "inner join users u on ut.iduser = u.iduser " +
                    "inner join reis r on t.idreis = r.idreis " +
                    "where t.idticket = '"+MyTicketsController.place+"'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id.setText(resultSet.getString(1));
                rid.setText(resultSet.getString(2));
                nameReis.setText(resultSet.getString(3));
                dateAndTime.setText(resultSet.getString(4));
                pib.setText(resultSet.getString(5));
                place.setText(resultSet.getString(6));
                fromId = resultSet.getInt(7);
                toId = resultSet.getInt(8);
            }
            PreparedStatement preparedStatement2 = Main.connection.prepareStatement("select cityname from cities where idcity = '"+fromId+"'");
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while(resultSet2.next()){
                from.setText(resultSet2.getString(1));
            }
            PreparedStatement preparedStatement3 = Main.connection.prepareStatement("select cityname from cities where idcity = '"+toId+"'");
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            while(resultSet3.next()){
                to.setText(resultSet3.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnTicket(){
        if (ConfirmBox.display()){
            try {
                PreparedStatement preparedStatement = Main.connection.prepareStatement(
                        "call return_ticket('"+id.getText()+"', '"+place.getText()+"')");
                preparedStatement.executeUpdate();
                Stage stage = (Stage) returnButton.getScene().getWindow();
                stage.close();
                ProfileController.windowMyTickets.close();
                MyTicketsController.isOpen = false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
