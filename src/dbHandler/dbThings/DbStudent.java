package dbHandler.dbThings;

import Classes.Student;
import Main.Main;
import alertsHandler.alertsMake;
import dbHandler.DbMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import mainBorder.borderController;

import java.io.*;
import java.sql.*;

public class DbStudent {

    private static DbStudent studentInstance;

    private static DbMain MainInstance = DbMain.getInstance() ;

    public ObservableList<Student> data = null ;

    private static Connection conn = MainInstance.conn;
    private PreparedStatement stat ;


    public static DbStudent getInstance() {
        if (studentInstance == null) {
            studentInstance = new DbStudent();
        }
        return studentInstance;
    }

    public DbStudent(){
        setTable();
    }


    public void fillData() throws Exception {
        data = FXCollections.observableArrayList();
        ResultSet resultSet = getAllResult();


        OutputStream os = null;
        Image image;

        while (resultSet.next()) {

            os = new FileOutputStream(new File("src/img/photo.jpg"));
            byte[] content = resultSet.getBytes("image");
            os.write(content);

            if (content.length > 0) {
                image = new Image("file:src/img/photo.jpg", 200, 200, true, true);

            } else {
                image = new Image("file:src/img/noImage.png", 200, 200, true, true);
            }

            Student std = new Student( resultSet.getString("id"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("gender"),
                    resultSet.getString("birthday"),
                    resultSet.getString("adresse"),
                    resultSet.getString("number"),
                    resultSet.getString("year"),
                    resultSet.getString("class"));
            std.setImage(image);

            data.add(std);

            System.out.println(DbMain.studentNumber);
            DbMain.studentNumber++;
        }
        os.close();

    }

    private ResultSet getAllResult() throws SQLException {
        String query = "SELECT * FROM students";
        stat = conn.prepareStatement(query);
        return stat.executeQuery();
    }

    public boolean insert(Student student) {
        try {


            String query = "INSERT INTO students (id,firstname,lastname,gender,birthday,adresse,number,year,class,image) values (?,?,?,?,?,?,?,?,?,?)";



            stat = conn.prepareStatement(query);
            stat.setString(1, student.getId());
            stat.setString(2, student.getFirstName());
            stat.setString(3, student.getLastName());
            stat.setString(4, student.getGender());
            stat.setString(5, student.getBirthday());
            stat.setString(6, student.getAdresse());
            stat.setString(7, student.getNumber());
            stat.setString(8, student.getYear());
            stat.setString(9, student.getClas());

            if(student.getImageUri()!=null){
                File file = new File(student.getImageUri());
                FileInputStream fis = new FileInputStream(file);
                stat.setBinaryStream(10, (InputStream) fis, (int) file.length());
            }

            stat.execute();

            return true;


        } catch (com.mysql.cj.jdbc.exceptions.PacketTooBigException tooBig) {
            boolean check = alertsMake.confirmationAlert("The image is too big !! Do you want to continue with no Image", borderController.getInstance().parent);
            if (check){
                try {
                    File file = new File("src/img/noImage.png");
                    FileInputStream fis= new FileInputStream(file);
                    stat.setBinaryStream(10,fis,(int)file.length());
                    stat.execute();
                    student.setImage(new Image("file:src/img/noImage.png",200,200,true,true));
                } catch (Exception e) {
                    System.out.println(e);
                }
                return true;

            }
            return false;


        }catch (IOException io){

            System.out.println(io);
            return false;

        }catch (SQLException sql ){
            System.out.println(sql);
            return false;
        }
    }

    public boolean delete(Student student) {
        try {

            String query = "DELETE FROM students WHERE firstname=? AND lastname =? AND gender=? AND birthday=?";

            stat = conn.prepareStatement(query);
            stat.setString(1, student.getFirstName());
            stat.setString(2, student.getLastName());
            stat.setString(3, student.getGender());
            stat.setString(4, student.getBirthday());


            stat.execute();

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }


    }

    public boolean update(String[] old, Student New)  {
        try {
            String query;
            if(New.getImageUri()!=null){
                query = "UPDATE students set id=?,firstname=?,lastname=?,gender=?,birthday=?,adresse=?,number=?,year=?,class=?,image=? " +
                        "where id=? AND firstname=? AND lastname=?";

            }else {
                query = "UPDATE students set id=?,firstname=?,lastname=?,gender=?,birthday=?,adresse=?,number=?,year=?,class=? " +
                        "where id=? AND firstname=? AND lastname=?";

            }


            stat = conn.prepareStatement(query);
            stat.setString(1, New.getId());
            stat.setString(2, New.getFirstName());
            stat.setString(3, New.getLastName());
            stat.setString(4, New.getGender());
            stat.setString(5, New.getBirthday());
            stat.setString(6, New.getAdresse());
            stat.setString(7, New.getNumber());
            stat.setString(8, New.getYear());
            stat.setString(9, New.getClas());

            if(New.getImageUri()!=null){

                File file = new File(New.getImageUri());
                FileInputStream fis = new FileInputStream(file);
                stat.setBinaryStream(10, (InputStream) fis, (int) file.length());
                stat.setString(11,old[0]);
                stat.setString(12,old[1]);
                stat.setString(13,old[2]);
            }else{
                stat.setString(10,old[0]);
                stat.setString(11,old[1]);
                stat.setString(12,old[2]);
            }


            System.out.println(stat);



            stat.execute();

            return true;

        } catch (com.mysql.cj.jdbc.exceptions.PacketTooBigException tooBig) {
            boolean check = alertsMake.confirmationAlert("The image is too big !! Do you want to continue with no Image", borderController.getInstance().parent);
            if (check){
                try {
                    File file = new File("src/img/noImage.png");
                    FileInputStream fis= new FileInputStream(file);
                    stat.setBinaryStream(10,fis,(int)file.length());
                    stat.execute();
                    New.setImage(new Image("file:src/img/noImage.png",200,200,true,true));
                } catch (Exception e) {
                    System.out.println(e);
                }
                return true;

            }
            return false;
        }catch (IOException io){

            System.out.println(io);
            return false;

        }catch (SQLException sql ){

            System.out.println(sql);
            return false;
        }
    }

    public void setTable(){
        String tableName= "Students";

        try{
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,tableName.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Student table exist !! u r ready ");
            }else {
                String query = "CREATE TABLE " +tableName + "(" +
                        "   id varchar(200) primary key,\n" +
                        "   firstname varchar(200),\n" +
                        "   lastname varchar(200),\n" +
                        "   gender varchar(200),\n" +
                        "   birthday varchar(200),\n" +
                        "   adresse varchar(200),\n" +
                        "   number varchar(200),\n" +
                        "   year varchar(200),\n" +
                        "   class varchar(200),\n" +
                        "  )";
                stat = conn.prepareStatement(query);
                stat.execute();
                System.out.println("student table created");
            }
        }catch (Exception e ){
            System.out.println("creating table : " + e);
        }
    }




}
