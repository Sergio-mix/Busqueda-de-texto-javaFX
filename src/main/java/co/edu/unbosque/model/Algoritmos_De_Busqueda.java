package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.List;

public class Algoritmos_De_Busqueda {

    public Algoritmos_De_Busqueda() {

    }

    public static List<List<Integer>> basic(String text, String buscar) {
        List<List<Integer>> list = new ArrayList<>();
        int s = buscar.length();
        String subString;

        for (int i = 0; i < text.length(); i++) {
            int z = i + s;

            List<Integer> l = new ArrayList<>();

            if (i + s <= text.length()) {
                subString = text.substring(i, z);
                if (subString.equals(buscar)) {
                    l.add(i);
                    l.add(z);
                    list.add(l);
                }

            } else {
                subString = text.substring(text.length() - s, text.length());
                if (subString.equals(buscar)) {
                    l.add(text.length() - s);
                    l.add(text.length());
                    list.add(l);
                }
            }
        }

        return list;
    }

    public static List<List<Integer>> kMP(String text, String buscar) {
        return null;
    }

    public static List<List<Integer>> bM(String text, String buscar) {
        return null;
    }

}
