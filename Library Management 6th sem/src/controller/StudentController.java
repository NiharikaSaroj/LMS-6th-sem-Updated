package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentController {

    @FXML
    private TextField studentNameField;

    @FXML
    private TextField courseField;

    @FXML
    private TableView<String> studentTable;

    @FXML
    private TableColumn<String, String> studentNameColumn;

    private ObservableList<String> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        studentNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        loadStudents();
    }

    @FXML
    public void addStudent() {
        String studentName = studentNameField.getText();

        if (studentName.isEmpty()) {
            showAlert("Error", "Student name cannot be empty.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO students (name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.executeUpdate();

            studentNameField.clear();
            loadStudents();

            showAlert("Success", "Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteStudent() {
        String selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {
            showAlert("Error", "Please select a student to delete.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "DELETE FROM students WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedStudent);
            stmt.executeUpdate();

            loadStudents();

            showAlert("Success", "Student deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        studentList.clear();

        try (Connection conn = DBConnection.connect()) {
            String sql = "SELECT name FROM students";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentList.add(rs.getString("name"));
            }

            studentTable.setItems(studentList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/books.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Library Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}