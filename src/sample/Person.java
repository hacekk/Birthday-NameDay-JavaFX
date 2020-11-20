package sample;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Person {

    private SimpleStringProperty firstName;
    private SimpleStringProperty secondName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfNameDay;
    private String note;

    static DateTimeFormatter formatterBith = DateTimeFormatter.ofPattern("d.M.y");
    static DateTimeFormatter formatterName = DateTimeFormatter.ofPattern("d.M.");

    NameDayCollection nameDayCollection = new NameDayCollection();

    public Person(String firstName, String secondName, LocalDate dateOfBirth,LocalDate dateOfNameDay ,String note) {

        if (firstName.equals("")) {
            this.firstName = new SimpleStringProperty("¯\\_( ͡❛ - ͡❛)_/¯");
        }else{
            this.firstName = new SimpleStringProperty(firstName);
        }

        if(secondName.equals("")){
            this.secondName = new SimpleStringProperty("¯\\_( ͡❛ - ͡❛)_/¯");
        }else{
            this.secondName = new SimpleStringProperty(secondName);
        }

        this.dateOfBirth = dateOfBirth;

        if(dateOfNameDay == null){
            this.dateOfNameDay = nameDayCollection.getNameDay(firstName);
        }else{
            this.dateOfNameDay = dateOfNameDay;
        }

        if (note.equals("")){
            this.note = " ";
        }else {
            this.note = note;
        }

    }


    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getSecondName() {
        return secondName.get();
    }

    public SimpleStringProperty secondNameProperty() {
        return secondName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfNameDay() {
        return dateOfNameDay;
    }

    public String getNote() {
        return note;
    }

    public String daysUntilBirthDay(){
        LocalDate today = LocalDate.now();

        if(dateOfBirth == null){
            return null;
        }

        LocalDate nextBday = dateOfBirth.withYear(today.getYear());

        if(nextBday.isBefore(today) || nextBday.isEqual(today)){
            nextBday = nextBday.plusYears(1);
        }

        int DaysToBDay = (int) ChronoUnit.DAYS.between(today, nextBday);

        return String.valueOf(DaysToBDay);

    }

    public String daysUntilNameDay(){
        LocalDate today = LocalDate.now();


        if(dateOfNameDay == null){
            return null;
        }

        LocalDate nextNameDay = dateOfNameDay.withYear(today.getYear());

        if(nextNameDay.isBefore(today) || nextNameDay.isEqual(today)){
            nextNameDay = nextNameDay.plusYears(1);
        }

        int DaysToNameDay = (int) ChronoUnit.DAYS.between(today, nextNameDay);

        return String.valueOf(DaysToNameDay);

    }

    public String age(){

        LocalDate today = LocalDate.now();
        Period age = Period.between(dateOfBirth,today);

        return String.valueOf(age.getYears());
    }


}
