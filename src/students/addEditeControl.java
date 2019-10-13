package students;


import alertsHandler.alertsMake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dbHandler.DbMain;
import dbHandler.dbThings.DbStudent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainBorder.borderController;
import Classes.Student;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addEditeControl implements Initializable {

    private static addEditeControl instance;

    public addEditeControl() {
        instance = this;
    }

    public static addEditeControl getInstance() {
        return instance;
    }


    FileChooser fileChooser=null;
    File file=null;
    Image image;

    DbStudent dbhandler;


    ToggleGroup gender;

    String selectedGendr = null;


    @FXML
    private Label haja;

    @FXML
    private JFXTextField idField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField lnameField;


    @FXML
    private JFXDatePicker datepicker;

    @FXML
    private JFXTextField adressField;

    @FXML
    private JFXTextField numberField;

    @FXML
    private JFXTextField yearField;

    @FXML
    private JFXTextField classField;

    @FXML
    private JFXRadioButton maleRadio;


    @FXML
    private JFXRadioButton femaleRadio;

    @FXML
    private JFXButton addButton;


    @FXML
    private Circle crcl;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbhandler = DbMain.getInstance().dbStudent;
        gender = new ToggleGroup();
        maleRadio.setToggleGroup(gender);
        femaleRadio.setToggleGroup(gender);
        file = null;


        if (studentController.getInstance().state == 1) {
            haja.setText("Add student");
            maleRadio.setOnAction(event -> selectedGendr = "male");
            femaleRadio.setOnAction(event -> selectedGendr = "female");
            addButton.setText("Add");
            addButton.setOnAction(event -> add());
        } else {
            haja.setText("Edite student");
            fill(studentController.getInstance().table.getSelectionModel().getSelectedItem());
            addButton.setText("Save");
            addButton.setOnAction(event -> edite());
        }


    }


    @FXML
    void cancle(ActionEvent event) throws IOException {
        boolean alertsCheck = alertsMake.confirmationAlert("Are you sure u want to cancle ?", borderController.getInstance().parent);
        if (alertsCheck) {
            studentController.getInstance().showShowScene();
        }

    }


    @FXML
    void chooseFile(ActionEvent event) {

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        file = fileChooser.showOpenDialog((Stage) lnameField.getScene().getWindow());
        if (file != null) {
            image = new Image(file.toURI().toString());
            crcl.setFill(new ImagePattern(image));

        }

    }

    boolean chekEmpty() {
        return (nameField.getText().isEmpty() ||
                idField.getText().isEmpty() ||
                lnameField.getText().isEmpty() ||
                (!maleRadio.isSelected() && !femaleRadio.isSelected()) ||
                datepicker.toString().isEmpty() ||
                adressField.getText().isEmpty() ||
                numberField.getText().isEmpty() ||
                yearField.getText().isEmpty() ||
                classField.getText().isEmpty());
    }

    void fill(Student std) {

        idField.setText(std.getId());
        nameField.setText(std.getFirstName());
        lnameField.setText(std.getLastName());

        if (std.getGender() == "male") {
            gender.selectToggle(maleRadio);
            selectedGendr= "male";
        } else {
            gender.selectToggle(femaleRadio);
            selectedGendr= "female";
        }

        datepicker.getEditor().setText(std.getBirthday());
        adressField.setText(std.getAdresse());
        numberField.setText(std.getNumber());
        yearField.setText(std.getYear());
        classField.setText(std.getClas());
        if(std.getImage()!=null){
            crcl.setStyle("-fx-fill: transarent");
            crcl.setFill(new ImagePattern(std.getImage()));
        }

    }


    void add() {
        if (chekEmpty()) {
            alertsMake.showSimpleAlert("Check all informations are filled ", borderController.getInstance().parent);
        } else {
            try {

                Student student = new Student(
                        idField.getText(),
                        nameField.getText(),
                        lnameField.getText(),
                        selectedGendr,
                        ((TextField) datepicker.getEditor()).getText(),
                        adressField.getText(),
                        numberField.getText(),
                        yearField.getText(),
                        classField.getText()
                );


                if (file != null) {
                    student.setImageUri(file.getAbsolutePath());

                    if (dbhandler.insert(student)) {
                        studentController.data.add(student);
                        alertsMake.showSimpleAlert("New student are added !", borderController.getInstance().parent);
                        studentController.getInstance().showShowScene();
                    } else {
                        alertsMake.showSimpleAlert("Somthing goes wrong !", borderController.getInstance().parent);
                    }


                }else{
                    Boolean check = alertsMake.confirmationAlert("There's no image selected do you wanna continue ?",borderController.getInstance().parent);
                    if(check){
                        File smethn = new File("src/img/noImage.png");
                        student.setImageUri(smethn.getAbsolutePath());
                        student.setImage(new Image("file:src/img/noImage.png" , 200,200,true,true));

                        if (dbhandler.insert(student)) {
                            studentController.data.add(student);
                            alertsMake.showSimpleAlert("New student are added !", borderController.getInstance().parent);
                            studentController.getInstance().showShowScene();
                        } else {
                            alertsMake.showSimpleAlert("Somthing goes wrong !", borderController.getInstance().parent);

                        }
                    }
                }



            } catch (Exception e) {
                System.out.println(e+" i don't know ");
            }
        }

    }

    void edite() {
        Student std = studentController.getInstance().table.getSelectionModel().getSelectedItem();
        String[] values = {std.getId(), std.getFirstName(), std.getLastName()};

        if (!chekEmpty()) {
            std.setId(idField.getText());
            std.setFirstName(nameField.getText());
            std.setLastName(lnameField.getText());
            std.setGender(selectedGendr);
            std.setBirthday( ((TextField) datepicker.getEditor()).getText());
            std.setAdresse(adressField.getText());
            std.setNumber(numberField.getText());
            std.setYear(yearField.getText());
            std.setClas(classField.getText());



            if (file != null) {
                std.setImageUri(file.getAbsolutePath());
                std.setImage(new Image(file.toURI().toString()));

                if (dbhandler.update(values, std)) {
                    alertsMake.showSimpleAlert("Done Updating !", borderController.getInstance().parent);
                    studentController.getInstance().showShowScene();
                } else {
                    alertsMake.showSimpleAlert("Somthing goes wrong", borderController.getInstance().parent);
                }
            }else {
                Boolean check = alertsMake.confirmationAlert("There's no image selected do you wanna continue ?",borderController.getInstance().parent);
                if(check){
                    if (dbhandler.update(values, std)) {
                        alertsMake.showSimpleAlert("Done Updating !", borderController.getInstance().parent);
                        studentController.getInstance().showShowScene();
                    } else {
                        alertsMake.showSimpleAlert("Somthing goes wrong", borderController.getInstance().parent);

                    }
                }
            }

        }else {
            alertsMake.showSimpleAlert("Check all informations are filled ", borderController.getInstance().parent);
        }


    }


}
