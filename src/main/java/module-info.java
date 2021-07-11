module GRAPH_moment {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.jfoenix;

    // specify the package the uses the modules
    opens com.github.mateuszpach.Asunkoban to javafx.fxml, javafx.controls;

    exports com.github.mateuszpach.Asunkoban;
}