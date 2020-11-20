package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class PersonController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField secondNameField;

    @FXML
    private DatePicker dateOfBirthField;

    @FXML
    private TextField noteField;

    NameDayCollection nameDayCollection = new NameDayCollection();

    public Person getNewPerson(){
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        LocalDate dateOfNameDay = nameDayCollection.getNameDay(firstNameField.getText());


        String note = noteField.getText();

        Person newPerson = new Person(firstName,secondName,dateOfBirth,dateOfNameDay,note);
        return newPerson;
    }

    public void editPerson(Person person) {

        firstNameField.setText(person.getFirstName());
        secondNameField.setText(person.getSecondName());
        dateOfBirthField.setValue(person.getDateOfBirth());
        noteField.setText(person.getNote());
    }


}
