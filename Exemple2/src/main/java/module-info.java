module edu.uqtr.exemple2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.uqtr.exemple2 to javafx.fxml;
    exports edu.uqtr.exemple2;
}