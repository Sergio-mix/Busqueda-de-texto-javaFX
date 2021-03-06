package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.List;

public class Algoritmos_De_Busqueda {

    public Algoritmos_De_Busqueda() {

    }
    /**
     * Este metodo hace una busqueda del texto de forma basica
     * @param text Este es el texto completo de donde se buscara la palabra
     * @param buscar Es el caracter o palabra buscada por el ususario
     */
    public static List<List<Integer>> basic(String text, String buscar) {
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
    /**
     * Este metodo hace una busqueda del texto usando el algoritmo KMP
     * @param text Este es el texto completo de donde se buscara la palabra
     * @param buscar Es el caracter o palabra buscada por el ususario
     */
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

    private static int max(int b) {
        return Math.max(1, b);
    }
    /**
     * Este metodo hace una busqueda del texto usando el algoritmo BM
     * @param text Este es el texto completo de donde se buscara la palabra
     * @param buscar Es el caracter o palabra buscada por el ususario
     */
    public static List<List<Integer>> bM(String text, String buscar) {
        try {
            List<List<Integer>> list = new ArrayList<>();

            int m = buscar.length();
            int n = text.length();

            int NO_OF_CHARS = 1000 * 1000 * 1000;
            int[] badchar = new int[NO_OF_CHARS];

            int s = 0;
            while (s <= (n - m)) {
                int j = m - 1;

                while (j >= 0 && buscar.charAt(j) == text.charAt(s + j))
                    j--;

                if (j < 0) {
                    List<Integer> l = new ArrayList<>();
                    l.add(s);
                    l.add(s + m);
                    list.add(l);

                    s += (s + m < n) ? m - badchar[text.charAt(s + m)] : 1;

                } else
                    s += max(j - badchar[text.charAt(s + j)]);
            }

            return list;

        } catch (Exception e) {
            return null;
        }
    }
}
