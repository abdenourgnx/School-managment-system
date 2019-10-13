package students;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Classes.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class show implements Initializable {

    private static show instence;

    public show() {
        instence = this;
    }

    public static show getInstence() {
        return instence;
    }


    @FXML
    private JFXTextField idField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField lnameField;

    @FXML
    private JFXTextField gndrField;

    @FXML
    private JFXTextField bdField;

    @FXML
    private JFXTextField adressField;

    @FXML
    private JFXTextField numberField;

    @FXML
    private JFXTextField yearField;

    @FXML
    private JFXTextField classField;

    @FXML
    private Circle crcl;

    @FXML
    private ImageView imageView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idField.setEditable(false);
        nameField.setEditable(false);
        lnameField.setEditable(false);
        bdField.setEditable(false);
        gndrField.setEditable(false);
        adressField.setEditable(false);
        numberField.setEditable(false);
        yearField.setEditable(false);
        classField.setEditable(false);

    }


    void fill(Student student) {
        idField.clear();
        nameField.clear();
        lnameField.clear();
        gndrField.clear();;
        bdField.clear();
        adressField.clear();
        numberField.clear();
        yearField.clear();
        classField.clear();
        crcl.setFill(null);


        idField.setText(student.getId());
        nameField.setText(student.getFirstName());
        lnameField.setText(student.getLastName());
        gndrField.setText(student.getGender());
        bdField.setText(student.getBirthday());
        adressField.setText(student.getAdresse());
        numberField.setText(student.getNumber());
        yearField.setText(student.getYear());
        classField.setText(student.getClas());
        if (student.getImage() != null) {
            crcl.setFill(null);
            crcl.setFill(new ImagePattern(student.getImage()));
        }

    }


}




