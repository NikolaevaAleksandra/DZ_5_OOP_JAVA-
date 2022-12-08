package util;
import model.Data;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
public class MyXmlRoutine {
    public static Data readData(String filename) {
        Data db = new Data();
        db.clear();
        if (new File(filename).exists()) {  
            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                
                Document document = builder.parse(xmlFile);
                document.getDocumentElement().normalize();

                Element root = document.getDocumentElement();  
                System.out.println(root.getNodeName());

                NodeList nList = document.getElementsByTagName("Abonent");  

                for (int i = 0; i < nList.getLength(); i++) {  
                    if (nList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nList.item(i);
                        ArrayList<String> phones = new ArrayList<String>();
                        for (int j = 0; j < eElement.getChildNodes().getLength(); j++) {  
                                                                                          
                            if (eElement.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                                phones.add(eElement.getChildNodes().item(j).getTextContent());
                            }
                        }
                        db.dataBase.put(eElement.getAttribute("name"), phones); 
                    }
                }
                System.out.printf("Данные прочитаны из файла %s%n", filename);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        else {
            System.out.printf("Файл %s не найден! Загружена пустая база данных!%n", filename);
        }
        return db;
    }

    public static void writeData(Data db, String filename) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            
            Document document = builder.newDocument();
           
            Element rootElement =
                    document.createElementNS("http://www.w3.org/2001/XMLSchema", "Abonents");
         
            document.appendChild(rootElement);

            Element element;
            for(String s: db.dataBase.keySet()) { 
                element = document.createElement("Abonent");
                element.setAttribute("name", s);  
                Element node;
                ArrayList<String> phones = db.dataBase.get(s);
                for(String p: phones) {  
                    node = document.createElement("phone");
                    node.appendChild(document.createTextNode(p));
                    element.appendChild(node);
                }
                rootElement.appendChild(element);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
          
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

           
            StreamResult file = new StreamResult(new File(filename));

            
            transformer.transform(source, file);
            System.out.printf("Данные записаны в файл %s%n",filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}
