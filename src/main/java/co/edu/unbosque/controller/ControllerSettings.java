package co.edu.unbosque.controller;

import co.edu.unbosque.persistence.OperacionArchivo;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettings implements Initializable {

    @FXML
    private JFXToggleButton blPresicion;

    @FXML
    private ComboBox<String> cbAlgoritmo;

    @FXML
    private ColorPicker pikerColor;

    @FXML
    private JFXSlider timeRange;

    @Override
    /**
     * Este metodo inicializa los valores de la pestaña propiedades
     * @param url Este es el texto completo de donde se buscara la palabra
     * @param buscar Es el caracter o palabra buscada por el ususario
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cbAlgoritmo.getItems().add(0, "Basic");
            cbAlgoritmo.getItems().add(1, "KMP");
            cbAlgoritmo.getItems().add(2, "BM");

            cbAlgoritmo.getSelectionModel().select(Integer.parseInt(String.valueOf(OperacionArchivo.getAjustes().get("algoritmo"))));
            blPresicion.setSelected((Boolean) OperacionArchivo.getAjustes().get("presicion"));

            pikerColor.setValue(Color.web(String.valueOf(OperacionArchivo.getAjustes().get("selectColor"))));
            timeRange.setValue((Double) OperacionArchivo.getAjustes().get("tiempo"));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Con este metodo se actualiza el algoritmo de busqueda deseado por el usuario
     */
    @FXML
    private void updateAlgoritmo() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("algoritmo", cbAlgoritmo.getSelectionModel().getSelectedIndex());
        operacionArchivo.escribirProperties("algoritmo", cbAlgoritmo.getSelectionModel().getSelectedIndex());
    }
    /**
     * Con este metodo se actualiza la opcion de precision en el algoritmo
     */
    @FXML
    private void updatePresicion() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("presicion", blPresicion.isSelected());
        operacionArchivo.escribirProperties("presicion", blPresicion.isSelected());
    }
    /**
     * Con este metodo se actuliza el color que el usuario quiere para resaltar el texto
     */
    @FXML
    private void updateColorSelect() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("selectColor", toHexString(pikerColor.getValue()));
        operacionArchivo.escribirProperties("selectColor", toHexString(pikerColor.getValue()));
    }
    /**
     * Con este metodo se actualiza el lapso de tiempo elegido en ajustes
     */
    @FXML
    private void updateTiempo() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("tiempo", timeRange.getValue());
        operacionArchivo.escribirProperties("tiempo", timeRange.getValue());
    }
    /**
     * Este metodo convierte cada valor de color a un string
     * @param val el valor que tiene el color
     */
    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }
    /**
     * Este metodo pasa a formato Hex el color elegido por el usuario
     * @param value es el valor que tiene el color elegido
     */
    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }
}
