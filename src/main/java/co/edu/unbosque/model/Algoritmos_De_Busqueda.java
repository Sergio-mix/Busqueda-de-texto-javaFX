package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.List;

public class Algoritmos_De_Busqueda {

    public Algoritmos_De_Busqueda() {

    }

    public static List<String> basic(String text, String buscar) {
        List<String> list = new ArrayList<>();
        int s = buscar.length();
        String subString;

        for (int i = 0; i < text.length(); i++) {
            int z = i + s;

            if (i + s <= text.length()) {
                subString = text.substring(i, z);
                if (subString.equals(buscar)) {
                    list.add(i + " " + z);
                }
            } else {
                subString = text.substring(text.length() - s, text.length());
                if (subString.equals(buscar)) {
                    list.add(text.length() - s + " " + text.length());
                }
            }
        }
        return list;
    }

    public static List<String> kMP(String text, String buscar) {
        return null;
    }

    public static List<String> bM(String text, String buscar) {
        return null;
    }

}
