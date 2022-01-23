module edu.uqtr.exemple1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.uqtr.exemple1 to javafx.fxml;
    exports edu.uqtr.exemple1;
}