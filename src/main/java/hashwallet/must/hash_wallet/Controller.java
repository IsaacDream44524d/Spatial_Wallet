package hashwallet.must.hash_wallet;

import hashwallet.must.hash_wallet.model.User;
import hashwallet.must.hash_wallet.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static hashwallet.must.hash_wallet.repository.UserRepository.retrieveUser;
import static hashwallet.must.hash_wallet.repository.UserRepository.save;
import static hashwallet.must.hash_wallet.service.AuthenticationService.authenticate;

public class Controller {

    @FXML private Button depositBtn;
    @FXML private Button transferBtn;
    @FXML private Button withdrawBtn;
    @FXML private VBox dashboard;
    @FXML private HBox step1Pane;
    @FXML private HBox step2Pane;
    @FXML private HBox step3Pane;
    @FXML private HBox step4Pane;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private Label emailLabel;
    @FXML Label passwordLabel;
    @FXML private Label errorLabel;
    @FXML Label avatar;
    @FXML private Label  balance;
    @FXML private Label balanceUSD;
    @FXML private CheckBox termsCheck;
    @FXML private TextField usernameField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Label confirmPasswordError;
    @FXML private Label depositLabel;
    @FXML private TextField depositField;

    @FXML private void removeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML public void createAccountPage(ActionEvent event) throws IOException {

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

    @FXML public void landingPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("landingPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.getScene().setRoot(root);

    }

    @FXML
    private void goToStep2() {

        String email = emailField.getText().trim();
        hideAllErrors();

        boolean hasError = false;

        if (firstNameField.getText().isBlank()) {
            showError(firstNameError, "This field is required");
            hasError = true;
        }

        if (lastNameField.getText().isBlank()) {
            showError(lastNameError, "This field is required");
            hasError = true;
        }

        if (emailField.getText().isBlank()) {
            showError(emailError, "This field is required");
            hasError = true;
        }

        // Optional: check username length
        if (!usernameField.getText().isBlank() && usernameField.getText().length() != 12) {
            // showError(usernameError, "Must be 12 characters");
            hasError = true;
        }


        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showError(emailError, "Please enter a valid email address.");
            return;
        }

        // UI demonstration only: simulate an existing account
        if (UserRepository.retrieveUser(email) != null) {
            showError(emailError,
                    "An account with this email already exists. Please sign in.");
            return;
        }

        if (hasError) {
            return; // stay on step 1
        }

        // Everything is valid → go to Step 2
        showStep(step2Pane);
    }

    @FXML private void goToStep1() {
        showStep(step1Pane);
    }

    @FXML private void goToStep4() {

        hideAllErrors();

        boolean hasError = false;

        if (depositField.getText().isBlank()) {
            showError(depositLabel, "This field is required");
            hasError = true;
        }

        try {
            BigDecimal deposit = new BigDecimal(depositField.getText().trim());

            if (deposit.compareTo(BigDecimal.ZERO) < 0) {
                showError(depositLabel, "Deposit can not be less than zero");
                hasError = true;
            }
        } catch (NumberFormatException exception) {
            showError(depositLabel, "Deposit MUST be a number");
            hasError = true;
        }

        if (hasError) {
            return; // stay on step 2
        }
        showStep(step4Pane);
    }

    @FXML
    private void goToStep3() {
        hideAllErrors();

        boolean hasError = false;

        if (passwordField.getText().isBlank()) {
            showError(passwordError, "This field is required");
            hasError = true;
        }

        if (confirmPasswordField.getText().isBlank()) {
            showError(confirmPasswordError, "This field is required");
            hasError = true;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showError(confirmPasswordError, "Passwords do not match");
            hasError = true;
        }

        if (hasError) {
            return; // stay on step 2
        }

        // Everything is valid → go to Step 3
        showStep(step3Pane);
    }

    public void setActionType(String actionType) {
        depositBtn.setText(actionType);
    }

    @FXML private void openDepositModal(ActionEvent event) {
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

    @FXML private void loginPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.getScene().setRoot(root);
    }

    @FXML private void dashboard(ActionEvent event) throws IOException {
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

    @FXML private void login(ActionEvent event) {

        // First hide all error messages
        hideAllErrors();

        boolean hasError = false;

        // Check Email
        if (emailField.getText().isBlank()) {
            showError(emailLabel, "This field is required");
            hasError = true;
        }

        // Check Password
        if (passwordField.getText().isBlank()) {
            showError(passwordLabel, "This field is required");
            hasError = true;
        }

        // If there is any error, stop here
        if (hasError) {
            return;
        }

         if (!termsCheck.isSelected()) {
             showError(errorLabel, "You must agree to the Terms and Conditions");
             return;
         }


        // login logic goes here
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // System.out.println(email+password);
        boolean isAuthenticated =authenticate(email, password);

        if (isAuthenticated) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hashwallet/must/hash_wallet/dashboard.fxml"));

                Parent root = loader.load();

                // Get the dashboard's controller
                Controller controller = loader.getController();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

                // Update using the correct controller instance
                controller.updateDashboard(email);

            } catch (Exception e) {
                e.printStackTrace();

        }
        } else {
            // Login failed
            showError(errorLabel, "Invalid credentials");
        }



    }


    //Helper methods
    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);   // makes it take space
    }

    private void hideError(Label label) {
        if (label != null) {
            label.setVisible(false);
            label.setManaged(false);
        }
    }

    private void hideAllErrors() {
        hideError(errorLabel);
        hideError(emailLabel);
        hideError(passwordLabel);
        hideError(firstNameError);
        hideError(lastNameError);
        hideError(emailError);
        hideError(passwordError);
        hideError(confirmPasswordError);
    }

    private void showStep(HBox stepToShow) {
        step1Pane.setVisible(false);
        step1Pane.setManaged(false);

        step2Pane.setVisible(false);
        step2Pane.setManaged(false);

        step3Pane.setVisible(false);
        step3Pane.setManaged(false);

        step4Pane.setVisible(false);
        step4Pane.setManaged(false);

        stepToShow.setVisible(true);
        stepToShow.setManaged(true);
    }

    private void updateDashboard(String email) {

        double exchangeRate = 1733.0;

        User user = retrieveUser(email);

        String initials = String.valueOf(user.getUsername().charAt(0));
        BigDecimal usdAmount = user.getWallet().getBalance().divide(BigDecimal.valueOf(exchangeRate), 2, RoundingMode.HALF_UP);

        avatar.setText(initials);
        balance.setText(String.valueOf(user.getWallet().getBalance()));
        balanceUSD.setText(String.valueOf(usdAmount));

        System.out.println("Avatar: " + avatar);
        System.out.println("Balance: " + balance);
        System.out.println("Balance USD: " + balanceUSD);





    }

    @FXML private void createUser() {

        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String username = firstNameField.getText().trim() + " " + lastNameField.getText().trim();

        BigDecimal amount = new BigDecimal(depositField.getText().trim());

        try {
            boolean saved = UserRepository.save(email, username, password, amount);

            if (saved) {
                goToStep4();
            } else {
                showError(depositLabel, "Wallet creation failed, please try again");
//                System.out.println("User registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
