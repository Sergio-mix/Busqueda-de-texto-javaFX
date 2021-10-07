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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cbAlgoritmo.getItems().add(0, "KMP");
            cbAlgoritmo.getItems().add(1, "BM");

            cbAlgoritmo.getSelectionModel().select(Integer.parseInt(String.valueOf(OperacionArchivo.getAjustes().get("algoritmo"))));
            blPresicion.setSelected((Boolean) OperacionArchivo.getAjustes().get("presicion"));

            pikerColor.setValue(Color.web(String.valueOf(OperacionArchivo.getAjustes().get("selectColor"))));
            timeRange.setValue((Double) OperacionArchivo.getAjustes().get("tiempo"));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void updateAlgoritmo() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("algoritmo", cbAlgoritmo.getSelectionModel().getSelectedIndex());
        operacionArchivo.escribirProperties("algoritmo", cbAlgoritmo.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void updatePresicion() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("presicion", blPresicion.isSelected());
        operacionArchivo.escribirProperties("presicion", blPresicion.isSelected());
    }

    @FXML
    private void updateColorSelect() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("selectColor", toHexString(pikerColor.getValue()));
        operacionArchivo.escribirProperties("selectColor", toHexString(pikerColor.getValue()));
    }

    @FXML
    private void updateTiempo() throws IOException {
        OperacionArchivo operacionArchivo = new OperacionArchivo();
        OperacionArchivo.getAjustes().put("tiempo", timeRange.getValue());
        operacionArchivo.escribirProperties("tiempo", timeRange.getValue());
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }
}
