package librarymanagementapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Login {

    public static boolean verifyUser(String username, String password) {
        boolean isValid = false;
        try {
            // Connect to the database
            Connection conn = DBConnection.getConnection();
            
            // Prepare SQL query to check username and password
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            // Execute the query
            ResultSet rs = pst.executeQuery();
            
            // If a row is returned, the user exists
            if (rs.next()) {
                isValid = true;
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static void main(String[] args) {
        // Ask user for username and password
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        // First, check if the username/password exists
        if (verifyUser(username, password)) {
            try {
                // Connect again to get the role
                Connection conn = DBConnection.getConnection();
                String sql = "SELECT role FROM users WHERE username=? AND password=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role"); // Get the role
                    if (role.equals("admin")) {
                    	JOptionPane.showMessageDialog(null, "Welcome Admin!");
                        
                        // Ask if admin wants to add a book
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to add a new book?", "Add Book", JOptionPane.YES_NO_OPTION);
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            AddBook.main(null); // Call AddBook class to add a book
                        } else {
                            JOptionPane.showMessageDialog(null, "You chose not to add a book now.");
                        }
                    
                    
                    
                    
                    } else {
                    	JOptionPane.showMessageDialog(null, "Welcome Student!");
                        
                        // Ask if student wants to view available books
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view available books?", "View Books", JOptionPane.YES_NO_OPTION);
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            ViewBooks.main(null); // Call ViewBooks class
                        } else {
                            JOptionPane.showMessageDialog(null, "You chose not to view books now.");
                        }
                        
                        int choiceIssue = JOptionPane.showConfirmDialog(null, "Do you want to issue a book?", "Issue Book", JOptionPane.YES_NO_OPTION);
                        
                        if (choiceIssue == JOptionPane.YES_OPTION) {
                            IssueBook.main(null); // Call IssueBook class
                        } else {
                            JOptionPane.showMessageDialog(null, "You chose not to issue a book now.");
                        }
                        
                        
                    }
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Invalid login
            JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
        }
    }
}

