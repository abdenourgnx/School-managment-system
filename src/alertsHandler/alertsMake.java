package alertsHandler;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class alertsMake {

    public static boolean showSimpleAlert( String content , Node parent) {
        BoxBlur blur = new BoxBlur(3,3,3);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("");
        alert.setHeaderText(null);
//
        ButtonType okBtn = new ButtonType("Ok");

//
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(okBtn);

        alert.setContentText(content);
        styleAlert(alert);
        parent.setEffect(blur);

//
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get()==okBtn){
            parent.setEffect(null);
            return true;
        }else {
            parent.setEffect(null);
            return false;
        }

    }


    public static boolean confirmationAlert(String content , Node parent){
        BoxBlur blur = new BoxBlur(3,3,3);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("hello");
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType yesbtn = new ButtonType("Yes");
        ButtonType canclebtn = new ButtonType("No");


        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yesbtn,canclebtn);

        styleAlert(alert);
        parent.setEffect(blur);

        Optional<ButtonType> option = alert.showAndWait();
        if(option.get()==yesbtn){
            parent.setEffect(null);
            return true;
        }else {
            parent.setEffect(null);
            return false;
        }
    }

    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Scene scene = alert.getDialogPane().getScene();
        Pane dialogePane = alert.getDialogPane();

        dialogePane.setPrefSize(500 ,Region.USE_COMPUTED_SIZE);



        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();



        scene.setFill(Color.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(alertsMake.class.getResource("/alertsHandler/alerts.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }
}
