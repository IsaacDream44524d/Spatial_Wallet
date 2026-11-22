package hashwallet.must.hash_wallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    @FXML
    private Button depositBtn;
    @FXML
    private Button transferBtn;
    @FXML
    private Button withdrawBtn;
    @FXML
    private VBox dashboard;
    @FXML
    private StackPane createAccountForm;
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

        Stage stage;

        if (event.getSource() instanceof MenuItem) {
            // Coming from a MenuItem
            stage = (Stage) dashboard.getScene().getWindow();
        } else {
            // Coming from a normal Button / Node
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

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
        step3Pane.setVisible(false);
        step3Pane.setManaged(false);

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

    public void setActionType(String actionType) {
        depositBtn.setText(actionType);
    }

    @FXML
    private void openDepositModal(ActionEvent event) {
        try {
            Button clickedBtn = (Button) event.getSource();

            String actionType;

            if (clickedBtn == depositBtn) {
                actionType = "Deposit";
            } else if (clickedBtn == transferBtn) {
                actionType = "Transfer";
            } else if (clickedBtn == withdrawBtn) {
                actionType = "Withdraw";
            } else {
                actionType = "Deposit";
            }

            FXMLLoader fxmlLoader = new FXMLLoader(
                    App.class.getResource("depositForm.fxml")
            );

            Parent modalRoot = fxmlLoader.load();

            // Retrieve the controller of the loaded FXML
            Controller controller = fxmlLoader.getController();

            // Pass the transaction type
            controller.setActionType(actionType);

            Stage modalStage = new Stage();
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.setResizable(false);
            modalStage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow()
            );

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.getScene().setRoot(root);
    }

    @FXML
    private void dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

        Stage stage;

        if (event.getSource() instanceof MenuItem) {
            // Coming from a MenuItem
            stage = (Stage) dashboard.getScene().getWindow();
        } else {
            // Coming from a normal Button / Node
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        stage.getScene().setRoot(root);
    }
}
