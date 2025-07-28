package Progetto.StaticClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicSwitch {
    private static Map<String, String> roles = new LinkedHashMap<>();

    public static void inizializzaMap(ArrayList<String> ruoli) {
        int num = 0;
        for (String role : ruoli) {
            num +=1;
            String numero = Integer.toString(num);
            roles.put(numero, role);
        }
        roles.put("0", "Indietro");
    }

    public static void stampaMap() {
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            System.out.println(entry.getKey() + ") " + entry.getValue());
        }
    }

    public static String sceltaUtente(String scelta) {
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            if (entry.getKey().equals(scelta)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
