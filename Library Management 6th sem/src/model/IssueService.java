package model;

public class IssueService {

    public String issueBook(String studentId, String bookId) {
        if (studentId.isEmpty() || bookId.isEmpty()) {
            return "Fill all fields!";
        }
        return "Book Issued Successfully!";
    }

    public String returnBook(String studentId, String bookId) {
        if (studentId.isEmpty() || bookId.isEmpty()) {
            return "Fill all fields!";
        }
        return "Book Returned Successfully!";
    }
}