module hashwallet.must.hash_wallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens hashwallet.must.hash_wallet to javafx.fxml;
    exports hashwallet.must.hash_wallet;
}