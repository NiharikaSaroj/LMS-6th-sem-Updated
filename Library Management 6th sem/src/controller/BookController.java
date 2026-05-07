package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.DBConnection;

import java.sql.*;

public class BookController {

    @FXML private TextField titleField, authorField, searchField;
    @FXML private ComboBox<String> categoryBox, statusBox;

    @FXML private Label totalBooksLabel, availableBooksLabel, issuedBooksLabel;

    @FXML private TableView<Book> bookTable;

    @FXML private TableColumn<Book, Integer> idColumn;
    @FXML private TableColumn<Book, String> titleColumn, authorColumn, categoryColumn, statusColumn;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    private Book selectedBook;

    @FXML
    public void initialize() {

        categoryBox.setItems(FXCollections.observableArrayList(
                "General", "Science", "Technology", "History", "Fiction"
        ));

        statusBox.setItems(FXCollections.observableArrayList(
                "Available", "Issued"
        ));

        idColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()).asObject());

        titleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTitle()));

        authorColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAuthor()));

        categoryColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCategory()));

        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus()));

        loadBooks();
        updateDashboard();
    }

    private void loadBooks() {

        bookList.clear();

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("status")
                ));
            }

            bookTable.setItems(bookList);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addBook() {

        if (titleField.getText().isEmpty() || authorField.getText().isEmpty()
                || categoryBox.getValue() == null || statusBox.getValue() == null) {

            showAlert("Error", "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DBConnection.connect();

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO books(title, author, category, status) VALUES(?,?,?,?)"
            );

            pstmt.setString(1, titleField.getText());
            pstmt.setString(2, authorField.getText());
            pstmt.setString(3, categoryBox.getValue());
            pstmt.setString(4, statusBox.getValue());

            pstmt.executeUpdate();

            conn.close();

            clearFields();
            loadBooks();
            updateDashboard();

            showAlert("Success", "Book added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectBook() {

        selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            titleField.setText(selectedBook.getTitle());
            authorField.setText(selectedBook.getAuthor());
            categoryBox.setValue(selectedBook.getCategory());
            statusBox.setValue(selectedBook.getStatus());
        }
    }

    @FXML
    private void updateBook() {

        if (selectedBook == null) {
            showAlert("Error", "Select a book first.");
            return;
        }

        try {
            Connection conn = DBConnection.connect();

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE books SET title=?, author=?, category=?, status=? WHERE id=?"
            );

            pstmt.setString(1, titleField.getText());
            pstmt.setString(2, authorField.getText());
            pstmt.setString(3, categoryBox.getValue());
            pstmt.setString(4, statusBox.getValue());
            pstmt.setInt(5, selectedBook.getId());

            pstmt.executeUpdate();

            conn.close();

            clearFields();
            loadBooks();
            updateDashboard();

            showAlert("Success", "Book updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteBook() {

        if (selectedBook == null) {
            showAlert("Error", "Select a book first.");
            return;
        }

        try {
            Connection conn = DBConnection.connect();

            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM books WHERE id=?"
            );

            pstmt.setInt(1, selectedBook.getId());
            pstmt.executeUpdate();

            conn.close();

            clearFields();
            loadBooks();
            updateDashboard();

            showAlert("Success", "Book deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchBooks() {

        String keyword = searchField.getText().toLowerCase();

        ObservableList<Book> filtered = FXCollections.observableArrayList();

        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword)) {
                filtered.add(book);
            }
        }

        bookTable.setItems(filtered);
    }

    private void updateDashboard() {

        totalBooksLabel.setText(String.valueOf(bookList.size()));

        int available = 0;
        int issued = 0;

        for (Book book : bookList) {
            if (book.getStatus().equalsIgnoreCase("Available")) {
                available++;
            } else {
                issued++;
            }
        }

        availableBooksLabel.setText(String.valueOf(available));
        issuedBooksLabel.setText(String.valueOf(issued));
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        categoryBox.setValue(null);
        statusBox.setValue(null);
        selectedBook = null;
    }

    @FXML
    private void openStudents() {
        openPage("/view/students.fxml", "Student Management");
    }

    @FXML
    private void openIssueSystem() {
        openPage("/view/issue.fxml", "Issue / Return System");
    }

    private void openPage(String path, String title) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) bookTable.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {

        Alert alert = title.equals("Error")
                ? new Alert(Alert.AlertType.ERROR)
                : new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}