package librarymanagementapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class AddBook {

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Get book details from admin
            String title = JOptionPane.showInputDialog("Enter Book Title:");
            String author = JOptionPane.showInputDialog("Enter Author Name:");
            String category = JOptionPane.showInputDialog("Enter Book Category:");
            
            // By default, book is available
            boolean available = true;

            // Insert book into database
            String sql = "INSERT INTO books (title, author, category, available) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, category);
            pst.setBoolean(4, available);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Book added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add book.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

