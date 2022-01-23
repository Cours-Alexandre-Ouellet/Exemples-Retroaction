module edu.uqtr.exemple3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.uqtr.exemple3 to javafx.fxml;
    exports edu.uqtr.exemple3;
}