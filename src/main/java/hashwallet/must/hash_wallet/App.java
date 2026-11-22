package hashwallet.must.hash_wallet;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;


import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("landingPage.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 674, 520);

        // Set window icon
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/money.png")));

        // stage.setTitle("Spatial Wallet");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}