module com.nimbus_railway.nimbusrailway {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nimbus_railway.nimbusrailway to javafx.fxml;
    exports com.nimbus_railway.nimbusrailway;
}