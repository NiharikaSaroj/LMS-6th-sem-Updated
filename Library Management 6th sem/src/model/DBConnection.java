package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection(URL);

            Statement stmt = conn.createStatement();

            // BOOKS TABLE
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "author TEXT NOT NULL," +
                    "category TEXT DEFAULT 'General'," +
                    "status TEXT DEFAULT 'Available'" +
                    ")");

            // STUDENTS TABLE
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "course TEXT NOT NULL" +
                    ")");

            // ISSUED BOOKS TABLE
            stmt.execute("CREATE TABLE IF NOT EXISTS issued_books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student_name TEXT NOT NULL," +
                    "book_title TEXT NOT NULL," +
                    "issue_date TEXT NOT NULL," +
                    "due_date TEXT NOT NULL," +
                    "status TEXT DEFAULT 'Issued'" +
                    ")");

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}