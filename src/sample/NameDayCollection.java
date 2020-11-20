package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NameDayCollection {

    private String NAME_DAY_FILE = "All_Name_Day.csv";
    private Map<String,String> nameDays = new HashMap<>();


    public LocalDate getNameDay(String name){
//        public Map<String, String> getNameDays(){

    try {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(NAME_DAY_FILE));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(";");
            nameDays.put(data[0], data[1]);
        }


    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

        if(nameDays.get(name) == null) {
            return null;
        }

        return LocalDate.parse(nameDays.get(name),Person.formatterBith);

    }

    public void addNewName(String name, String date){

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NAME_DAY_FILE,true));
            bufferedWriter.write(name + ";" + date);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
