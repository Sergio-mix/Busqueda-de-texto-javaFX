module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens co.edu.unbosque.controller to javafx.fxml;
    opens co.edu.unbosque.persistence;
    opens co.edu.unbosque.model;
    exports co.edu.unbosque.controller;
    exports co.edu.unbosque.persistence;
    exports co.edu.unbosque.model;
}