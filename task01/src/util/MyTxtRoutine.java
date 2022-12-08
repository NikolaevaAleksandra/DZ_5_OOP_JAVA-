package util;
import model.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class MyTxtRoutine {
    public static Data readData(String filename) {
        Data db = new Data();
        db.clear();
        if (new File(filename).exists()) {  
            try {
                List<String> lines = Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8); // Читаем сразу все
                boolean isNewAbonent = true;
                for(String s: lines) {
                    if (s.length() == 0) {  
                        isNewAbonent = true;
                    }
                    else {
                        if (isNewAbonent) {
                            db.addRecord(s); 
                            isNewAbonent = false;
                        }
                        else {
                            db.addPhone(s);  
                        }
                    }
                }
                System.out.printf("Данные прочитаны из файла %s%n", filename);
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else {
            System.out.printf("Файл %s не найден! Загружена пустая база данных!%n", filename);
        }

        return db;
    }
    public static void writeData(Data db, String filename) {
        try(FileWriter writer = new FileWriter(filename, false))
        {
            for(String s: db.dataBase.keySet()) {   
                writer.write(String.format("%s%n",s));  
                ArrayList<String> phones = db.dataBase.get(s);
                for (String p: phones) {
                    writer.write(String.format("%s%n",p));  
                }
                writer.write(String.format("%n"));
            }
            writer.flush();
            System.out.printf("Данные записаны в файл %s%n",filename);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
