package co.edu.unbosque.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    
    /**
     * Este metodo inicializa el programa, carga los iconos y los textos
     * @param stage se le envia un parametro de tipo Stage que es el contenedor de la parte visual en el programa
     */
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/icons/file_1.png"))));
        stage.setTitle("Selector de Archivos");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    /**
     * Este metodo hace una busqueda del texto usando el algoritmo BM
     * @param fxml Este es el texto completo de donde se buscara la palabra
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }

}