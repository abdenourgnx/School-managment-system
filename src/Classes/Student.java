package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;


public class Student extends Grade{

    private final StringProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gender;
    private final StringProperty birthday;
    private final StringProperty adresse;
    private final StringProperty number;
    private final StringProperty year;
    private final StringProperty clas;
    private  String imageUri=null;
    private  Image image= null;




    public Student(String idd,String fName, String lName , String Sgender, String Sbd,String adres,String nmbr,String yr,String cls) {
        this.id = new SimpleStringProperty(idd);
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.birthday = new SimpleStringProperty(Sbd);
        this.gender = new SimpleStringProperty(Sgender);
        this.adresse = new SimpleStringProperty(adres);
        this.number = new SimpleStringProperty(nmbr);
        this.year = new SimpleStringProperty(yr);
        this.clas = new SimpleStringProperty(cls);
//        this.imageUri = imageUri;
//        this.image = img;
    }


    public String getId() { return id.get(); }
    public void setId(String idd) { id.set(idd); }
    public StringProperty idProperty() { return id; }


    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String fName) { firstName.set(fName); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String lName) { lastName.set(lName); }
    public StringProperty lastNameProperty() { return lastName; }


    public void setGender(String Sgender) { gender.set(Sgender); }
    public StringProperty genderProperty() { return gender; }
    public String getGender() { return gender.get(); }

    public void setBirthday(String Sbd) { birthday.set(Sbd); }
    public StringProperty birthdayProperty() { return birthday; }
    public String getBirthday() { return birthday.get(); }

    public void setAdresse(String adrs) { adresse.set(adrs); }
    public StringProperty adresseProperty() { return adresse; }
    public String getAdresse() { return adresse.get(); }


    public void setNumber(String nbr) { number.set(nbr); }
    public StringProperty numberProperty() { return number; }
    public String getNumber() { return number.get(); }


    public void setYear(String yr) { year.set(yr); }
    public StringProperty yearProperty() { return year; }
    public String getYear() { return year.get(); }


    public void setClas(String cls) { clas.set(cls); }
    public StringProperty clasProperty() { return clas; }
    public String getClas() { return clas.get(); }

    public void setImageUri(String imageUri){ this.imageUri=imageUri; }
    public String getImageUri(){ return imageUri ;}

    public void setImage(Image img){image = img;}
    public Image getImage(){return image;}



}

