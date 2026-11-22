module hashwallet.must.hash_wallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    requires javafx.graphics;
    requires org.hibernate.orm.core;

    opens hashwallet.must.hash_wallet to javafx.fxml;
    exports hashwallet.must.hash_wallet;
}