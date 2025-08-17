package librarymanagementapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class ViewBooks {

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT book_id, title, author, category, available FROM books";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("book_id"))
                  .append(", Title: ").append(rs.getString("title"))
                  .append(", Author: ").append(rs.getString("author"))
                  .append(", Category: ").append(rs.getString("category"))
                  .append(", Available: ").append(rs.getBoolean("available") ? "Yes" : "No")
                  .append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No books found.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
