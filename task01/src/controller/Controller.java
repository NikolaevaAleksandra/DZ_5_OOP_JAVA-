package controller;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

import model.Data;
import util.MyJsonRoutine;
import util.MyXmlRoutine;
import util.MyTxtRoutine;
import util.Settings;
import view.View;


public class Controller {  
    private final Logger log = Logger.getLogger(Controller.class.getName());
    Data db = new Data();
    View view = new View(this.log);  
    public Controller() {  
        try {
            LogManager.getLogManager().readConfiguration( 
                    Controller.class.getResourceAsStream("../log.config"));
        } catch (IOException e) {  
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }
    public void run() throws IOException, ParseException {  
        boolean ex = false;
        Scanner scan = new Scanner(System.in);
        String inputLine = "";
        view.info();

        loadFromJSON(Settings.db);  

        while (!ex) {  
            System.out.print(">>> ");
            inputLine = scan.nextLine();
            if (inputLine.length()>0) {
                switch (inputLine.split(" ",2)[0]) {
                    
                    case "/quit" -> ex = true;         
                    case "/info" -> this.view.info();  
                    case "/help" -> this.view.help();  
                    case "/addRecord" -> view.addRecord(db, inputLine.split(" ",2)[1]);  
                    case "/addPhone" -> view.addPhone(db ,inputLine.split(" ",2)[1]);   
                    case "/printAll" -> view.printAll(this.db);     
                    case "/printCurrent" -> view.printCurrent(this.db);  
                    case "/setRecord" -> setRecord(inputLine.split(" ",2)[1]);        
                    case "/saveJSON" -> saveToJSON(inputLine.split(" ",2)[1]);          
                    case "/loadJSON" -> loadFromJSON(inputLine.split(" ",2)[1]);        
                    case "/saveXML" -> saveToXML(inputLine.split(" ",2)[1]);           
                    case "/loadXML" -> loadFromXML(inputLine.split(" ",2)[1]);          
                    case "/saveTXT" -> saveToTXT(inputLine.split(" ",2)[1]);            
                    case "/loadTXT" -> loadFromTXT(inputLine.split(" ",2)[1]);         
                    default -> view.errorCommand(inputLine);
                }
            }
        }
        saveToJSON(Settings.db);
        view.bye();
    }

    private void setRecord(String record) {  
        if (db.dataBase.containsKey(db.getCurrent())) {
            db.setCurrent(record);
            System.out.printf("Текущая запись %s установлена!%n", record);
        }
        else {
            System.out.printf("Записи с именем %s не существует!%n Текущей записью остается %s%n", record, db.getCurrent());
        }
    }
    private void loadFromJSON(String file) throws IOException, ParseException {
        this.db = MyJsonRoutine.readData(file);

        Map.Entry<String, ArrayList> entry = db.dataBase.entrySet().iterator().next();
        db.setCurrent(entry.getKey());
    }

    private void saveToJSON(String file) throws IOException {
        MyJsonRoutine.writeData(this.db,file);
    }
    private void loadFromXML(String file) throws IOException, ParseException {
        this.db = MyXmlRoutine.readData(file);

        Map.Entry<String, ArrayList> entry = db.dataBase.entrySet().iterator().next();
        db.setCurrent(entry.getKey());
    }

    private void saveToXML(String file) throws IOException {
        MyXmlRoutine.writeData(this.db,file);
    }

    private void loadFromTXT(String file) throws IOException, ParseException {
        this.db = MyTxtRoutine.readData(file);

        Map.Entry<String, ArrayList> entry = db.dataBase.entrySet().iterator().next();
        db.setCurrent(entry.getKey());
    }

    private void saveToTXT(String file) throws IOException {
        MyTxtRoutine.writeData(this.db,file);
    }
}