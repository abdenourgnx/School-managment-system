package students;

import alertsHandler.alertsMake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mainBorder.borderController;
import Classes.Student;
import dbHandler.DbMain;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class studentController implements Initializable {

    private static studentController instance;

    public studentController() {
        instance = this;
    }

    public static studentController getInstance() {
        return instance;
    }


    DbMain handler;
    String currentSideScene;

    int state;


    @FXML
    private Pane pane;

    @FXML
    private JFXTextField searchField;

    @FXML
    private ComboBox<String> yearChoice;
    @FXML
    private ComboBox<String> classChoice;


    @FXML
    public TableView<Student> table;
    @FXML
    TableColumn col0, col1, col2, col3, col4;


    static ObservableList<Student> data = null;


    @FXML
    private JFXButton addbtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton editeBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = DbMain.getInstance();
        try {
            pane.getChildren().clear();
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("show.fxml")));

        } catch (Exception e) {
            e.printStackTrace();
        }


        addbtn.setOnAction(event -> showAddEdite(1));
        editeBtn.setOnAction(event -> showAddEdite(0));

        table.setOnMousePressed(e -> showSelection(table.getSelectionModel().getSelectedItem()));

        ObservableList<String> choice1 = FXCollections.observableArrayList("All","year1", "year2", "year3");
        ObservableList<String> choice2 = FXCollections.observableArrayList("All","class1", "class2", "class3");


        data = handler.stdData;
        SortedList<Student> sortedData = new SortedList<>(data);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        col0.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        col1.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        col2.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        col3.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        col4.setCellValueFactory(new PropertyValueFactory<Student, String>("birthday"));


        table.setItems(sortedData);
        table.getSortOrder().add(col0);
        yearChoice.setItems(choice1);



        FilteredList<Student> filteredListSearch = new FilteredList<>(data, e -> true);


        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredListSearch.setPredicate((Predicate<? super Student>) student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCase = newValue.toLowerCase();
                    if (student.getId().toLowerCase().contains(lowerCase)) {
                        return true;
                    } else if (student.getFirstName().toLowerCase().contains(lowerCase)) {
                        return true;
                    } else if (student.getLastName().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Student> sortedList = new SortedList<>(filteredListSearch);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);
        });

        FilteredList<Student> filteredListyear = new FilteredList<>(data, e -> true);

        yearChoice.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredListyear.setPredicate((Predicate<? super Student>) student-> {
                    if(newValue==null || newValue.equals("All")){
                        classChoice.setItems(null);
                        return true;
                    }
                    String lowerCase = newValue.toLowerCase();

                    if(student.getYear().toLowerCase().equals(lowerCase)){
                        classChoice.setItems(choice2);
                        return true;
                    }
                    return false;
                });
                table.setItems(filteredListyear);
            }

        });

        FilteredList<Student> filteredListclass = new FilteredList<>(data, e -> true);

          classChoice.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredListclass.setPredicate((Predicate<? super Student>) student-> {
                    if(newValue==null ){
                        return true;
                    }
                    String lowerCase = newValue.toLowerCase();

                    if(student.getClas().toLowerCase().equals(lowerCase) && student.getYear().toLowerCase().equals(yearChoice.getSelectionModel().getSelectedItem())){
                        return true;
                    }else if(student.getYear().toLowerCase().equals(yearChoice.getSelectionModel().getSelectedItem()) && lowerCase.equals("all")){
                        return true;
                    }
                    return false;
                });
                table.setItems(filteredListclass);
            }
        });

    }


    void showAddEdite(int h) {


        state = h;

        if(h==0 && table.getSelectionModel().getSelectedItem() == null){
            alertsMake.showSimpleAlert("Please select a student to edite", borderController.getInstance().parent);
        }else{
            try {
                Stage stage = (Stage) pane.getScene().getWindow();
                pane.getChildren().clear();
                pane.getChildren().add(FXMLLoader.load(getClass().getResource("addOrEdite.fxml")));
                currentSideScene = "add";
                stage.sizeToScene();
                desible();

            } catch (Exception e) {
                System.out.println(e);

            }
        }

    }


    void showShowScene() {
        try {
            pane.getChildren().clear();
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("show.fxml")));
            Stage stage =(Stage) pane.getScene().getWindow();
            stage.sizeToScene();
            currentSideScene = "show";
            enable();

        } catch (Exception e) {
            System.out.println(e + "show");

        }

    }


    @FXML
    void deletItems(ActionEvent event) {
        Student student = table.getSelectionModel().getSelectedItem();

        if (student == null) {
            alertsMake.showSimpleAlert("Please select a student to delete", borderController.getInstance().parent);
        } else {
            Boolean checkAlert = alertsMake.confirmationAlert("Are you sure want to delete " + table.getSelectionModel().getSelectedItem().getFirstName() + " from the list ?", borderController.getInstance().parent);
            if (checkAlert) {

                if (DbMain.dbStudent.delete(student)) {
                    data.remove(student);
                } else {
                    alertsMake.showSimpleAlert("Error aquired please check db connection", borderController.getInstance().parent);
                }


            }
        }


    }

    void showSelection(Student student) {
        if (student != null) {
            show.getInstence().fill(student);
        }
    }


    void desible() {
        searchField.setDisable(true);
        table.setDisable(true);
        deleteBtn.setDisable(true);
        editeBtn.setDisable(true);
        yearChoice.setDisable(true);
        classChoice.setDisable(true);
        addbtn.setDisable(true);


    }

    void enable() {
        searchField.setDisable(false);
        table.setDisable(false);
        deleteBtn.setDisable(false);
        editeBtn.setDisable(false);
        yearChoice.setDisable(false);
        classChoice.setDisable(false);
        addbtn.setDisable(false);
    }


}
