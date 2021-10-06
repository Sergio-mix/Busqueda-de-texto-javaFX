package co.edu.unbosque.persistence;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class OperacionArchivo {

    private static Map<String, Object> ajustes = new HashMap<>();
    private static Window stage;

    public OperacionArchivo() {

    }

    public static String cargarArchivo() {
        FileReader fr = null;
        BufferedReader br;
        StringBuilder result = new StringBuilder();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Buscar txt");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All txt", "*.*"),
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );

            File txtFile = fileChooser.showOpenDialog(stage);

            if (txtFile != null) {
                fr = new FileReader(txtFile);
                br = new BufferedReader(fr);

                String linea;
                while ((linea = br.readLine()) != null) {
                    result.append(linea).append("\n");
                }
                return result.toString();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public void leerProperties() throws IOException {
        Properties p = new Properties();
        p.load(new FileReader("src\\main\\resources\\database\\file.properties"));

        ajustes.put("algoritmo", Integer.parseInt(Objects.requireNonNull(p.getProperty("algoritmo"))));
        ajustes.put("presicion", Boolean.parseBoolean(Objects.requireNonNull(p.getProperty("presicion"))));
        ajustes.put("selectColor", Objects.requireNonNull(p.getProperty("selectColor")));
        ajustes.put("tiempo", Double.parseDouble(Objects.requireNonNull(p.getProperty("tiempo"))));
    }

    public void escribirProperties(String propiedad, Object valor) throws IOException {
        Properties p = new Properties();
        p.load(new FileReader("src\\main\\resources\\database\\file.properties"));
        p.setProperty(propiedad, String.valueOf(valor));
        p.store(new FileWriter("src\\main\\resources\\database\\file.properties"), "");
    }

    public static Map<String, Object> getAjustes() {
        return ajustes;
    }

}
