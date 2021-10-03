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
import javafx.scene.text.Text;



public class Controller implements Initializable {

    @FXML
    private TextField txtBuscar;

    @FXML
    private Text txtContador;

    @FXML
    private ComboBox<String> cbAlgoritmo;

    @FXML
    private JFXTextArea txtTexto;

    private static String texto = null;

    private Thread hilo;

    private static final int TIEMPO = 800;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbAlgoritmo.getItems().add(0, "Basic");
            cbAlgoritmo.getItems().add(1, "KMP");
            cbAlgoritmo.getItems().add(2, "BM");
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
                                select(Algoritmos_De_Busqueda.basic(texto, busqueda));
                                break;
                            case 1:
                                select(Algoritmos_De_Busqueda.kMP(texto, busqueda));
                                break;
                            case 2:
                                select(Algoritmos_De_Busqueda.bM(texto, busqueda));
                                break;
                        }
                    } else {
                        mostrarAlertWarning("Verifique que halla seleccionado el método de búsqueda");
                    }
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
    private void select(List<List<Integer>> range) {
        try {
            txtTexto.deselect();
            txtTexto.setStyle("-fx-highlight-fill: #ffe800;");
            txtContador.setText(range.size() + " Resultados");

            if (range.size() != 1) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (List<Integer> i : range) {
                                txtTexto.selectRange(i.get(0), i.get(1));
                                Thread.sleep(TIEMPO);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        hilo.stop();
                    }
                };

                hilo = new Thread(runnable);
                hilo.start();
            } else {
                txtTexto.selectRange(range.get(0).get(0), range.get(0).get(1));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    private void reset() {
        try {
            txtTexto.setStyle("-fx-highlight-fill: #878686;");
            txtContador.setText("");
            txtBuscar.setText("");
            hilo.stop();
        } catch (Exception ignore) {

        }
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
