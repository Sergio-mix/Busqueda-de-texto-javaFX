package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.List;

public class Algoritmos_De_Busqueda {

    public Algoritmos_De_Busqueda() {

    }

    public static List<List<Integer>> kMP(String text, String buscar) {
        try {
            List<List<Integer>> list = new ArrayList<>();

            int M = buscar.length();
            int N = text.length();

            int[] lps = new int[M];
            int j = 0;

            int i = 0;
            while (i < N) {
                if (buscar.charAt(j) == text.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    List<Integer> l = new ArrayList<>();

                    l.add(i - j);
                    l.add(i);
                    list.add(l);

                    j = lps[j - 1];
                } else if (i < N && buscar.charAt(j) != text.charAt(i)) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }

            return list;

        } catch (Exception e) {
            return null;
        }
    }

    public static List<List<Integer>> bM(String text, String buscar) {
        try {
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
                        i += s - 1;
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

        } catch (Exception e) {
            return null;
        }
    }
}
