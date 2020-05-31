package project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

    static boolean answer;

    public static boolean display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Підтвердіть дію");
        window.setMinWidth(300);
        Label label = new Label();
        label.setText("Ви впевнені, що хочете повернути квиток?");
        Button yesButton = new Button("Так");
        Button noButton = new Button("Ні");

        yesButton.setOnAction(e->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
