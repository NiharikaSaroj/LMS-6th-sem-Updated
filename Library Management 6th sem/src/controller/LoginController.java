package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    public void initialize() {

        roleBox.setItems(FXCollections.observableArrayList(
                "Admin",
                "User"
        ));
    }

    @FXML
    private void login(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleBox.getValue();

        // Default Credentials
        // Admin -> admin / admin123
        // User -> user / user123

        boolean valid = false;

        if (role == null) {
            showAlert("Error", "Please select role.");
            return;
        }

        if (role.equals("Admin")) {

            if (username.equals("admin") && password.equals("admin123")) {
                valid = true;
            }

        } else if (role.equals("User")) {

            if (username.equals("user") && password.equals("user123")) {
                valid = true;
            }
        }

        if (valid) {

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/books.fxml")
                );

                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

                stage.setScene(scene);
                stage.setTitle("Library Management System");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            showAlert("Login Failed", "Invalid credentials.");
        }
    }

    private void showAlert(String title, String message) {

        Alert alert;

        if (title.equals("Error") || title.equals("Login Failed")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
