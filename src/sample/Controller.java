package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Controller {

    @FXML
    private GridPane mainPanel;

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> secondNameColumn;
    @FXML
    private TableColumn<Person, LocalDate> birthDayColumn;
    @FXML
    private TableColumn<Person, LocalDate> nameDayColumn;
    @FXML
    private Label notes;
    @FXML
    private Label birthField1,birthField2,birthField3,birthField4,birthField5;
    @FXML
    private Label nameField1,nameField2,nameField3,nameField4,nameField5;


    private PersonCollection personCollection;

    @FXML
    public void initialize(){
        personCollection = new PersonCollection();
        personCollection.readPersonCollection();
        personTableView.setItems(personCollection.getPersonList());

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("secondName"));
        birthDayColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("dateOfBirth"));
        birthDayColumn.setCellFactory(new Callback<TableColumn<Person, LocalDate>, TableCell<Person, LocalDate>>() {
            @Override
            public TableCell<Person, LocalDate> call(TableColumn<Person, LocalDate> col) {
                return new TableCell<Person, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setText(null);
                        else if (item == null)
                            setText(null);
                        else
                            setText(String.format(item.format(Person.formatterBith)));
                    }

                };
            }
        });

        nameDayColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("dateOfNameDay"));
        nameDayColumn.setCellFactory(new Callback<TableColumn<Person, LocalDate>, TableCell<Person, LocalDate>>() {
            @Override
            public TableCell<Person, LocalDate> call(TableColumn<Person, LocalDate> col) {
                return new TableCell<Person, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setText(null);
                        else if (item == null)
                            setText(null);
                        else
                            setText(String.format(item.format(Person.formatterName)));
                    }

                };
            }
        });


        ageAndBDayUpdate();

    }

    @FXML
    public void ageAndBDayUpdate(){

        List<String> birthday = new ArrayList<>();
        List<String> nameDay  = new ArrayList<>();

        for (Person person : personCollection.getPersonList() ){

            if(!(person.daysUntilBirthDay() == null)){

                birthday.add(person.getFirstName() + " " + person.getSecondName() +" will be "+ person.age() +" years old in " + person.daysUntilBirthDay() + " days.");
            }

        }

        for (Person person : personCollection.getPersonList() ){

            if(!(person.daysUntilNameDay() == null)){

                nameDay.add(person.getFirstName() + " " + person.getSecondName() + " will have a name day in " + person.daysUntilNameDay() + " days.");
            }

        }

        Collections.sort(birthday, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }

        });

        Collections.sort(nameDay, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }

        });

        if(birthday.size() >= 1){
            birthField1.setText(birthday.get(0));
        }else{
            birthField1.setText(" ");
        }

        if(birthday.size() >= 2){
            birthField2.setText(birthday.get(1));
        }else{
            birthField2.setText(" ");
        }

        if(birthday.size() >= 3){
            birthField3.setText(birthday.get(2));
        }else{
            birthField3.setText(" ");
        }

        if(birthday.size() >= 4){
            birthField4.setText(birthday.get(3));
        }else{
            birthField4.setText(" ");
        }

        if(birthday.size() >= 5){
            birthField5.setText(birthday.get(4));
        }else{
            birthField5.setText(" ");

        }


        if(nameDay.size() >= 1){
            nameField1.setText(nameDay.get(0));
        }else{
            nameField1.setText(" ");
        }

        if(nameDay.size() >= 2){
            nameField2.setText(nameDay.get(1));
        }else{
            nameField2.setText(" ");
        }

        if(nameDay.size() >= 3){
            nameField3.setText(nameDay.get(2));
        }else{
            nameField3.setText(" ");
        }

        if(nameDay.size() >= 4){
            nameField4.setText(nameDay.get(3));
        }else{
            nameField4.setText(" ");
        }

        if(nameDay.size() >= 5){
            nameField5.setText(nameDay.get(4));
        }else{
            nameField5.setText(" ");
        }

    }


    @FXML
    public void personNote(){
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();

        if(selectedPerson == null){
                    return;
        }

        notes.setText(selectedPerson.getNote());
    }


    @FXML
    public void addPerson(){
        Dialog<ButtonType> buttonDialog = new Dialog<ButtonType>();
        buttonDialog.initOwner(mainPanel.getScene().getWindow());
        buttonDialog.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPersonWindow.fxml"));

        try {
            buttonDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = buttonDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            PersonController personController = fxmlLoader.getController();
            Person newPerson = personController.getNewPerson();
            personCollection.addPerson(newPerson);
            personCollection.savePersonCollection();

            ageAndBDayUpdate();
        }

    }

    @FXML
    public void deletePersonFromTable(){
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if(selectedPerson == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No person selected");
            alert.setHeaderText(null);
            alert.setContentText("Pease select the contact you want to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete person");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected contact: " + selectedPerson.getFirstName() + " " + selectedPerson.getSecondName());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            personCollection.deletePerson(selectedPerson);
            personCollection.savePersonCollection();

            ageAndBDayUpdate();
        }

    }


    @FXML
    public void editPersonFromTable(){
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if(selectedPerson == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No person selected");
            alert.setHeaderText(null);
            alert.setContentText("Pease select the person you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> buttonDialog = new Dialog<ButtonType>();
        buttonDialog.initOwner(mainPanel.getScene().getWindow());
        buttonDialog.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPersonWindow.fxml"));


        try {
            buttonDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        PersonController personController = fxmlLoader.getController();
        personController.editPerson(selectedPerson);

        Optional<ButtonType> result = buttonDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            personCollection.deletePerson(selectedPerson);
            Person newPerson = personController.getNewPerson();
            personCollection.addPerson(newPerson);
            personCollection.savePersonCollection();

            ageAndBDayUpdate();
        }

    }

    @FXML
    public void editNameDayFromTable(){
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();

        if(selectedPerson == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No person selected");
            alert.setHeaderText(null);
            alert.setContentText("Pease select the person you want to edit");
            alert.showAndWait();
            return;
        }

        if(selectedPerson.getFirstName().equals("¯\\_( ͡❛ - ͡❛)_/¯")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No window");
            alert.setHeaderText(null);
            alert.setContentText("Nooo no no no no!");
            alert.showAndWait();
            return;
        }


        Dialog<ButtonType> buttonDialog = new Dialog<ButtonType>();
        buttonDialog.initOwner(mainPanel.getScene().getWindow());
        buttonDialog.setTitle("Edit/Add Name Day date for: " + selectedPerson.getFirstName());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewNameDate.fxml"));

        try {
            buttonDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        buttonDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NameDayCollection nameDayCollection = new NameDayCollection();
        NameDayController nameDayController = fxmlLoader.getController();

        Optional<ButtonType> result = buttonDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            if(nameDayCollection.getNameDay(selectedPerson.getFirstName()) == null){
                nameDayController.addNewNameDate(selectedPerson.getFirstName());
                initialize();
            }else {
                personCollection.deletePerson(selectedPerson);
                Person newPerson = nameDayController.updateNewNameDate(selectedPerson.getFirstName(),selectedPerson.getSecondName(),selectedPerson.getDateOfBirth(),selectedPerson.getNote());
                personCollection.addPerson(newPerson);
                personCollection.savePersonCollection();

            }



        }


    }


}
