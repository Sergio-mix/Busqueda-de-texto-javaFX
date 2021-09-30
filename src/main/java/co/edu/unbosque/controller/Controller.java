package co.edu.unbosque.controller;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.unbosque.model.Algoritmos_De_Busqueda;
import co.edu.unbosque.persistence.OperacionArchivo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXTextArea;


public class Controller implements Initializable {

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cbAlgoritmo;

    @FXML
    private JFXTextArea txtTexto;

    private static String texto = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbAlgoritmo.getItems().add(0, "KMP");
            cbAlgoritmo.getItems().add(1, "BM");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método para cargar txt
     */
    @FXML
    private void cargarArchivo() {
        try {
            texto = OperacionArchivo.cargarArchivo();

            if (texto != null) {
                txtTexto.setText(texto);
                mostrarAlertInfo("Archivo cargado");
            }

        } catch (Exception e) {
            mostrarAlertError("Error al cargar el archivo inténtelo de nuevo");
        }
    }

    /**
     * Método para buscar
     */
    @FXML
    private void buscar() {
        try {
            String busqueda = txtBuscar.getText();

            if (texto != null) {

                if (!busqueda.equals("")) {

                    if (!cbAlgoritmo.getSelectionModel().isEmpty()) {

                        switch (cbAlgoritmo.getSelectionModel().getSelectedIndex()) {

                            case 0:
                                select(Algoritmos_De_Busqueda.kMP(busqueda, texto));
                                break;
                            case 1:
                                select(Algoritmos_De_Busqueda.bM(busqueda, texto));
                                break;
                        }
                    } else {
                        mostrarAlertWarning("Verifique que halla seleccionado el método de búsqueda");
                    }
                } else {
                    mostrarAlertWarning("Que desea buscar?");
                }
            } else {
                mostrarAlertWarning("Primero seleccione el archivo txt");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método para resaltar por rango
     *
     * @param range lista de rangos
     */
    @FXML
    private void select(List<Integer[]> range) {
        try {
            txtTexto.setStyle("-fx-highlight-fill: #ffe800;");

            for (Integer[] i : range) {
                txtTexto.selectRange(i[0], i[1]);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void au() {
        txtTexto.setStyle("-fx-highlight-fill: #878686;");
    }


    /**
     * Alert de error
     *
     * @param text texto del alert
     */
    @FXML
    private void mostrarAlertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Alert de info
     *
     * @param text texto del alert
     */
    @FXML
    private void mostrarAlertInfo(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Alert de advertencia
     *
     * @param text texto del alert
     */
    @FXML
    private void mostrarAlertWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Advertencia");
        alert.setContentText(text);
        alert.showAndWait();
    }

}
