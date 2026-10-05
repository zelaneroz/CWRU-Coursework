module dev.krupp.passwordmanager {
    requires javafx.fxml;
    requires atlantafx.base;


    opens edu.cwru.passwordmanager to javafx.fxml;
    exports edu.cwru.passwordmanager;
}