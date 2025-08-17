package librarymanagementapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class IssueBook {

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Ask student for username
            String username = JOptionPane.showInputDialog("Enter your username:");
            
            // Get user_id from username
            String sqlUser = "SELECT user_id FROM users WHERE username=?";
            PreparedStatement pstUser = conn.prepareStatement(sqlUser);
            pstUser.setString(1, username);
            ResultSet rsUser = pstUser.executeQuery();
            
            if (!rsUser.next()) {
                JOptionPane.showMessageDialog(null, "User not found!");
                conn.close();
                return;
            }
            
            int userId = rsUser.getInt("user_id");
            
            // Show all available books
            StringBuilder sb = new StringBuilder();
            String sqlBooks = "SELECT book_id, title FROM books WHERE available=1";
            PreparedStatement pstBooks = conn.prepareStatement(sqlBooks);
            ResultSet rsBooks = pstBooks.executeQuery();
            
            while (rsBooks.next()) {
                sb.append("ID: ").append(rsBooks.getInt("book_id"))
                  .append(", Title: ").append(rsBooks.getString("title"))
                  .append("\n");
            }
            
            if (sb.length() == 0) {
                JOptionPane.showMessageDialog(null, "No available books to issue.");
                conn.close();
                return;
            }
            
            JOptionPane.showMessageDialog(null, sb.toString());
            
            // Ask student which book to issue
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID to issue:"));
            
            // Insert into issued_books
            String sqlIssue = "INSERT INTO issued_books (book_id, user_id, issue_date) VALUES (?, ?, CURDATE())";
            PreparedStatement pstIssue = conn.prepareStatement(sqlIssue);
            pstIssue.setInt(1, bookId);
            pstIssue.setInt(2, userId);
            pstIssue.executeUpdate();
            
            // Update book availability
            String sqlUpdate = "UPDATE books SET available=0 WHERE book_id=?";
            PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, bookId);
            pstUpdate.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Book issued successfully!");
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
