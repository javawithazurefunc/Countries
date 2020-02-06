package Data;

import java.util.HashMap;
import java.util.Map;

public class Countries {
    public Map<Integer, String> GetCountriesList()
    {
        Map<Integer, String> c =  new HashMap<Integer, String>();
        c.put(1, "India");
        c.put(2, "USA");
        c.put(3, "UK");

        return c;
    } 
}