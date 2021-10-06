package co.edu.unbosque.controller;


import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import co.edu.unbosque.model.Algoritmos_De_Busqueda;
import co.edu.unbosque.persistence.OperacionArchivo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Controller implements Initializable {

    @FXML
    private TextField txtBuscar;

    @FXML
    private Label txtContador;

    @FXML
    private JFXTextArea txtTexto;

    private static String texto = null;

    private Thread hilo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            OperacionArchivo operacionArchivo = new OperacionArchivo();
            operacionArchivo.leerProperties();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para cargar txt
     */
    @FXML
    private void cargarArchivo() {
        try {
            var result = OperacionArchivo.cargarArchivo();

            if (result != null) {
                texto = result;
            }

            if (texto != null) {
                txtTexto.setText(texto);
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
                    if ((Boolean) OperacionArchivo.getAjustes().get("presicion")) {
                        switch ((Integer) OperacionArchivo.getAjustes().get("algoritmo")) {
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
                        switch ((Integer) OperacionArchivo.getAjustes().get("algoritmo")) {
                            case 0:
                                select(Algoritmos_De_Busqueda.basic(texto.toLowerCase(Locale.ROOT), busqueda.toLowerCase(Locale.ROOT)));
                                break;
                            case 1:
                                select(Algoritmos_De_Busqueda.kMP(texto.toLowerCase(Locale.ROOT), busqueda.toLowerCase(Locale.ROOT)));
                                break;
                            case 2:
                                select(Algoritmos_De_Busqueda.bM(texto.toLowerCase(Locale.ROOT), busqueda.toLowerCase(Locale.ROOT)));
                                break;
                        }
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
            txtTexto.setStyle("-fx-highlight-fill:" + OperacionArchivo.getAjustes().get("selectColor") + ";");
            txtContador.setText(range.size() + " Resultados");

            if (range.size() != 1) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (List<Integer> i : range) {
                                txtTexto.selectRange(i.get(0), i.get(1));
                                Thread.sleep((long) ((Double) OperacionArchivo.getAjustes().get("tiempo") * 1000));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
    private void abrirAjustes() {
        try {
            this.reset();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/unbosque/controller/settings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            ControllerSettings controllerSettings = (ControllerSettings) fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Ajustes");
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/icons/file_1.png"))));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception ignored) {

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
