package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class NameDayController {

    @FXML
    private DatePicker nameDayDate;

    private NameDayCollection nameDayCollection = new NameDayCollection();

    public void addNewNameDate(String name){

        nameDayCollection.addNewName(name, String.valueOf(nameDayDate.getValue().format(Person.formatterBith)));

    }

    public Person updateNewNameDate(String firstName, String secondName, LocalDate dateOfBirth, String note){

        Person person = new Person(firstName,secondName,dateOfBirth,nameDayDate.getValue(),note);
        return person;
    }



}
