package view;
import model.Data;

import java.util.ArrayList;
import java.util.logging.Logger;

public class View {
    private final Logger log;
    public View(Logger log) {
        this.log = log;
    }  
    public void info() {  
        log.info("/info");
        System.out.println("Программа - телефонный справочник");
        System.out.println("Для помощи наберите \"/help\"");
    }
    public void bye() {  
        log.info("/quit");
        System.out.println("Программа завершена.");
    }
    public void errorCommand(String cmd) {  
        log.info(String.format("Error command: \"%s\"\n",cmd));
        System.out.println("Неверная команда. Для помощи наберите \"help\"");
    }
    public void help() {  
        log.info("/help");
        System.out.println("Программа - поддерживает следующие команды:");
        System.out.println("/help - вывод помощи");
        System.out.println("/info - вывод информации о программе");
        System.out.println("/printAll - печать всей базы целиком");
        System.out.println("/printCurrent - печать текущей записи базы данных");
        System.out.println("/addRecord <name> - добавление записи с именем <name>");
        System.out.println("/addPhone <phoneNumber> - добавление номера телефона <phoneNumber> в текущую запись");
        System.out.println("/setRecord <name> - установка записи <name> в качестве текущей");
        System.out.println("/saveJSON <file> - сохранение базы в файл <file> в формате JSON");
        System.out.println("/loadJSON <file> - чтение из файла <file> в формате JSON базы данных");
        System.out.println("/saveTXT <file> - сохранение базы в файл <file> в формате TXT");
        System.out.println("/loadTXT <file> - чтение из файла <file> в формате TXT базы данных");
        System.out.println("/saveXML <file> - сохранение базы в файл <file> в формате XML");
        System.out.println("/loadXML <file> - чтение из файла <file> в формате XML базы данных");
    }
    public void printCurrent(Data db) {  
        log.info(String.format("/printCurrent %s",db.getCurrent()));
        if (!db.dataBase.containsKey(db.getCurrent())) {
            System.out.println("Записи с таким именем нет!");
        }
        else {
            ArrayList<String> phones = db.dataBase.get(db.getCurrent());
            System.out.printf("Имя: %s%n",db.getCurrent());  
            if (phones.size() == 0) {
                System.out.printf("Список телефонов у %s пуст.%n", db.getCurrent());
            }
            else {
                for(String s: phones) {  
                    System.out.printf("\t %s%n",s);
                }
            }
        }
    }

    public void printAll(Data db) { 
        log.info("/printAll");
        String saveCurrent = db.getCurrent();
        for(String s: db.dataBase.keySet()) {  
            db.setCurrent(s);
            printCurrent(db);
            System.out.println();  
        }
        db.setCurrent(saveCurrent);
    }
    public void addRecord(Data db, String name) {  
        log.info(String.format("/addRecord %s", name));
        if (name.length() > 0) {
            if (db.addRecord(name) == 0) {
                System.out.println("Запись добавлена.");
            }
            else {
                System.out.println("Запись с таким именем уже есть!");
            }
        }
        else {
            System.out.println("Укажите имя абонента!\n Запись не добавлена!");
        }
    }

    public void addPhone(Data db,String phone) {  
        log.info(String.format("/addPhone %s > %s", db.getCurrent(),phone));
        if (db.getCurrent().length() == 0) {
            System.out.println("База данных пуста! Не к чему добавлять телефон!");
            System.out.println("Сначала добавьте запись в базу данных!");
        }
        else {
            if (phone.length() > 0) {
                db.addPhone(phone);
                System.out.println("Номер телефона добавлен.");
            }
            else {
                System.out.println("Укажите номер телефона!\n Номер телефона не добавлен!");
            }
        }
    }   
}
