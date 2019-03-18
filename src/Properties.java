import java.util.HashMap;
import java.util.Map;

public class Properties {

    private static Properties properties;
    public Map<String, Property> propertyMap;

    Properties(){
        propertyMap = new HashMap<>();
    }

    public static Properties getProperties(){
        if(properties == null){
            properties = new Properties();
        }
        return properties;
    }

    public boolean addProperty(Property property){
        if(propertyMap.containsKey(property.getAddress())){
            return false;
        }else {
            propertyMap.put(property.getAddress(), property);
            return true;
        }
    }

    public int getAmountOfExistingProperties(){
        return propertyMap.size();
    }
}
