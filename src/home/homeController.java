package home;

import dbHandler.dbThings.DbStudent;
import dbHandler.DbMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mainBorder.borderController;

import java.net.URL;
import java.util.ResourceBundle;

public class homeController implements Initializable {

    DbMain dbhandler;
    DbStudent dbStudent ;

    private double xOffset = 0;
    private double yOffset = 0;

    Stage stage = null;

    @FXML
    private Label exit;

    @FXML
    private Label studentInfo;


    @FXML
    Pane studentsPan;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbhandler = DbMain.getInstance();
        studentInfo.setText(String.valueOf(dbhandler.getStudentNumber()) + " student");

        studentsPan.setOnMousePressed(event -> {
            borderController.getInstance().change("Students");
        });

    }


    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();

    }
}
