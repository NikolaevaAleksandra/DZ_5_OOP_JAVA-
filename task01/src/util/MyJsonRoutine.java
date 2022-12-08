package util;
import model.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
public class MyJsonRoutine {
    public static Data readData(String filename) throws IOException, ParseException {
        Data db = new Data();
        db.clear();
        if (new File(filename).exists()) {
            FileReader reader = new FileReader(filename);               
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(reader); 
            for(String s: (Set<String>)jsonObj.keySet()) {              
                ArrayList<String> phones = new ArrayList<String>();     
                db.dataBase.put(s, phones);
                phones.addAll((ArrayList<String>) jsonObj.get(s));  
            System.out.printf("Данные прочитаны из файла %s%n", filename);
        }
        else {
            System.out.printf("Файл %s не найден! Загружена пустая база данных!%n", filename);
        }

        return db;
    }

    public static void writeData(Data db, String filename) throws IOException {
        JSONObject jsonObj = new JSONObject();
        for(String s: db.dataBase.keySet()) {   
            JSONArray jsonArr = new JSONArray();
            ArrayList<String> phones = db.dataBase.get(s);
            jsonArr.addAll(phones);  
            jsonObj.put(s,jsonArr);
        }
        Files.write(Paths.get(filename), jsonObj.toJSONString().getBytes());  
        System.out.printf("Данные записаны в файл %s%n",filename);
    }  
}
