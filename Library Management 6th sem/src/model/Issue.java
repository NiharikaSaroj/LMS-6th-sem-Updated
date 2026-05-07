package model;

public class Issue {

    private String studentName;
    private String bookTitle;
    private String issueDate;
    private String dueDate;

    public Issue(String studentName, String bookTitle, String issueDate, String dueDate) {
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}