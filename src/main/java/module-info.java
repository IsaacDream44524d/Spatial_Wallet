module hashwallet.must.hash_wallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens hashwallet.must.hash_wallet to javafx.fxml;
    opens hashwallet.must.hash_wallet.model to org.hibernate.orm.core;
    exports hashwallet.must.hash_wallet;
}