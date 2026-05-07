package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class DashboardController {

    // MUST MATCH FXML EXACTLY

    @FXML
    private void openBooks(ActionEvent event) {
        loadPage("/view/books.fxml", event);
    }

    @FXML
    private void openStudents(ActionEvent event) {
        loadPage("/view/students.fxml", event);
    }

    @FXML
    private void openIssue(ActionEvent event) {
        loadPage("/view/issue.fxml", event);
    }

    private void loadPage(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}