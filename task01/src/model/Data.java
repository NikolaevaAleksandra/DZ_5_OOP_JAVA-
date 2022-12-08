package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Data {
    public static Map<String, ArrayList> dataBase = new HashMap<String, ArrayList>();
    private String current = "";  
    public Data() {

    }

    public void setCurrent(String s) {  
        this.current = s;
    }
    public String getCurrent() {
        return this.current;
    }

    public int addRecord(String name) { 
        if (!dataBase.containsKey(name)) {
            dataBase.put(name, new ArrayList<String>());
            setCurrent(name);  
            return 0;
        } else {
            return -1;
        }
    }
    public void addPhone(String phone) {  
        ArrayList<String> phones = dataBase.get(this.current);
        phones.add(phone);
    }

    public void clear() {  
        setCurrent("");
        dataBase.clear();
    }
}
}
