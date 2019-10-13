package login;

import com.jfoenix.controls.*;
import dbHandler.DbMain;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Classes.httpConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {


    DbMain dbhandler;


    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    private JFXTextField nameField;


    @FXML
    private JFXPasswordField passField;

    @FXML
    private Label exit;

    @FXML
    Label errorLabel;

    @FXML
    private ImageView loading;


    String name, passwoerd;
    int result;


    Task<Integer> task;
    Thread myThread;
    Service service;
    Stage stage;
    Parent root;

    httpConnection http = new httpConnection();


    @FXML
    void clicked(ActionEvent event) {

        if (nameField.getText().isEmpty() && passField.getText().isEmpty()) {
            errorLabel.setText("Please fill both fields");
            errorLabel.setVisible(true);
        } else {
            passwoerd = passField.getText();
            name = nameField.getText();
            errorLabel.setVisible(false);

            service.cancel();
            service.restart();
            System.out.println("start");

        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loading.setVisible(false);
        errorLabel.setVisible(false);

        service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        loading.setVisible(true);
                        result = http.getAthe("http://172.20.12.240:88/login", name, passwoerd);
                        DbMain.getInstance();
                        loading.setVisible(false);
                        return null;
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            if (result == 200) {
                dbhandler = DbMain.getInstance();
                try {
                    stage = (Stage) passField.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("../mainBorder/border.fxml"));
                    mouvment(stage, root);
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    service.cancel();
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            } else {
                errorLabel.setText("Username or Password is uncorrect");
                errorLabel.setVisible(true);
                passField.clear();
                loading.setVisible(false);

            }
            System.out.println("finished");


        });


        passField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    clicked(new ActionEvent());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

    }


    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();

    }


    void mouvment(Stage stage, Parent root) {

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }





}




