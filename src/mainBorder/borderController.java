package mainBorder;

import alertsHandler.alertsMake;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class borderController implements Initializable {

    private static borderController instance;

    public borderController() {
        instance = this;
    }

    public static borderController getInstance() {
        return instance;
    }


     StringProperty currentScene = new SimpleStringProperty();


    @FXML
    private Pane pane;

    @FXML
    public HBox parent;

    @FXML
    private HBox homeBtn;

    @FXML
    private HBox studentsBtn;

    @FXML
    private HBox teachersBtn;

    @FXML
    private HBox paymentBtn;

    @FXML
    private Label exit;

    @FXML
    private Label title;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.textProperty().bind(currentScene);
        try {
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("../home/home.fxml")));
            AnchorPane Apane = (AnchorPane) pane.getChildren().get(0);
            pane.setPrefSize(Apane.getPrefWidth(), Apane.getPrefHeight());
        } catch (Exception e) {
            System.out.println(e);
        }
        homeBtn.setOnMousePressed(event -> change("Home"));
        studentsBtn.setOnMousePressed(event -> change("Students"));
        currentScene.set("Home");


    }

    public void change(String name) {
        try {

            if (name != currentScene.get()) {
                pane.getChildren().clear();
                Stage window = (Stage) pane.getScene().getWindow();
                switch (name) {
                    case "Students":
                        pane.getChildren().add(FXMLLoader.load(getClass().getResource("../students/students.fxml")));
                        break;
                    case "Home":
                        pane.getChildren().add(FXMLLoader.load(getClass().getResource("../home/home.fxml")));
                        break;
                    case "teachers":
                        pane.getChildren().add(FXMLLoader.load(getClass().getResource("../home/home.fxml")));
                        break;
                    case "payment":
                        pane.getChildren().add(FXMLLoader.load(getClass().getResource("../home/home.fxml")));
                        break;
                }
                window.sizeToScene();
                window.centerOnScreen();
                currentScene.set(name);

            }
        } catch (Exception e) {

        }


    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) homeBtn.getScene().getWindow();
        boolean alertsCheck = alertsMake.confirmationAlert("Are you sure u want to exite ?", borderController.getInstance().parent);
        if(alertsCheck){
           stage.close();
        }

    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) homeBtn.getScene().getWindow();

        stage.setIconified(true);
    }
}
