package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.time.LocalDate;

public class PersonCollection {

    private  final String PERSON = "person";
    private  final String FIRST_NAME = "first_name";
    private  final String SECOND_NAME = "second_name";
    private  final String BIRTH_DATE = "birth_date";
    private  final String NAME_DAY_DATE = "name_day_date";
    private  final String NOTE = "note";

    private final String PATH_STORED_DATA = "personCollection.xml";

    private ObservableList<Person> personList;

    public PersonCollection() {
        personList = FXCollections.observableArrayList();
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void addPerson(Person person){
        personList.add(person);
    }

    public void deletePerson(Person person){
        personList.remove(person);
    }


    public void readPersonCollection(){

        String first = null;
        String second = null;
        LocalDate birth = null;
        LocalDate nameDate = null;
        String note = null;

        try {

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(PATH_STORED_DATA);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);



            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();

//                    if(startElement.getName().getLocalPart().equals(PERSON)){
//                        continue;
//                    }

                    if(startElement.getName().getLocalPart().equals(FIRST_NAME)){
                        event = eventReader.nextEvent();
                        first = event.asCharacters().getData();
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(SECOND_NAME)){
                        event = eventReader.nextEvent();
                        second = event.asCharacters().getData();
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(BIRTH_DATE)){
                        event = eventReader.nextEvent();
                        if(event.asCharacters().getData().equals("null")) {
                            birth = null;
                        }else {
                            birth = LocalDate.parse(event.asCharacters().getData());
                        }

                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(NAME_DAY_DATE)){
                        event = eventReader.nextEvent();

                        if(event.asCharacters().getData().equals("null")) {
                            nameDate = null;
                        }else{
                            nameDate = LocalDate.parse(event.asCharacters().getData());
                        }
                        continue;
                    }

                    if(startElement.getName().getLocalPart().equals(NOTE)){
                        event = eventReader.nextEvent();
                        note = event.asCharacters().getData();
                        continue;
                    }

                }

                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals(PERSON)){
//                        personList.add(new Person(first,second,birth,nameDay));
                        personList.add(new Person(first,second,birth,nameDate,note));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }


    }

    public void savePersonCollection(){

        try{



        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter
                (new FileOutputStream(PATH_STORED_DATA));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);

        StartElement personStartElement = eventFactory.createStartElement
                ("","","personCollection");
        eventWriter.add(personStartElement);
        eventWriter.add(end);

        for (Person person: personList){
            savePerson(eventWriter,eventFactory,person);
        }

        eventWriter.add(eventFactory.createEndElement("", "", "personCollection"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Can not find persons file: " + e.getMessage());
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            System.out.println("Can not save person collection: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void savePerson(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Person person)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        //open tag
        StartElement startElement = eventFactory.createStartElement("","",PERSON);
        eventWriter.add(startElement);
        eventWriter.add(end);

        //content nodes
        createNode(eventWriter, FIRST_NAME, person.getFirstName());
        createNode(eventWriter, SECOND_NAME, person.getSecondName());
        createNode(eventWriter, BIRTH_DATE, String.valueOf(person.getDateOfBirth()));
        createNode(eventWriter, NAME_DAY_DATE, String.valueOf(person.getDateOfNameDay()));
        createNode(eventWriter, NOTE, person.getNote());

        //close tag
        eventWriter.add(eventFactory.createEndElement("", "", PERSON));
        eventWriter.add(end);
    }

    private void createNode (XMLEventWriter eventWriter, String name, String value) throws XMLStreamException{

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        //Start node
        StartElement startElement = eventFactory.createStartElement("","",name);
        eventWriter.add(tab);
        eventWriter.add(startElement);

        //Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        //End node
        EndElement endElement = eventFactory.createEndElement("","",name);
        eventWriter.add(endElement);
        eventWriter.add(end);
    }
















}
