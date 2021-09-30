package co.edu.unbosque.persistence;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class OperacionArchivo {

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
}
