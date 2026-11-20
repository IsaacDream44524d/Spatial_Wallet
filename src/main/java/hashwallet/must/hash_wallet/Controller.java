package hashwallet.must.hash_wallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button removeWindowBtn;
    @FXML
    private HBox step1Pane;
    @FXML
    private HBox step2Pane;
    @FXML
    private HBox step3Pane;


    @FXML
    private void removeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void createAccountPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("createAccountPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.getScene().setRoot(root);

    }

    @FXML
    public void landingPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("landingPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.getScene().setRoot(root);

    }


    @FXML
    private void showStep(HBox stepToShow) {
        // hide all
        step1Pane.setVisible(false);
        step1Pane.setManaged(false);
        step2Pane.setVisible(false);
        step2Pane.setManaged(false);
//        step3Pane.setVisible(false);
//        step3Pane.setManaged(false);

        // show the one we want
        stepToShow.setVisible(true);
        stepToShow.setManaged(true);
    }

    @FXML
    private void goToStep2() {
        showStep(step2Pane);
    }

    @FXML
    private void goToStep1() {
        showStep(step1Pane);
    }

    @FXML
    private void goToStep3() {
        showStep(step3Pane);
    }



}
