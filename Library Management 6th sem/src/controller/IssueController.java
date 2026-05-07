package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.IssueService;

public class IssueController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField bookIdField;

    @FXML
    private Label statusLabel;

    // MVC → Service Layer
    private IssueService issueService = new IssueService();

    // =========================
    // ISSUE BOOK
    // =========================
    @FXML
    public void handleIssueBook(ActionEvent event) {
        String studentId = studentIdField.getText();
        String bookId = bookIdField.getText();

        String result = issueService.issueBook(studentId, bookId);
        statusLabel.setText(result);
    }

    // =========================
    // RETURN BOOK
    // =========================
    @FXML
    public void handleReturnBook(ActionEvent event) {
        String studentId = studentIdField.getText();
        String bookId = bookIdField.getText();

        String result = issueService.returnBook(studentId, bookId);
        statusLabel.setText(result);
    }

    // =========================
    // NAVIGATION → DASHBOARD
    // =========================
    @FXML
    public void openDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}