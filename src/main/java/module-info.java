module com.nimbus_railway {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.nimbus_railway to javafx.fxml;
    opens com.nimbus_railway.controllers to javafx.fxml;
    opens com.nimbus_railway.models to javafx.base;
    exports com.nimbus_railway;
}
