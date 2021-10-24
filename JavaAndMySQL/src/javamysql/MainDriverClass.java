package javamysql;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author Student
 */
public class MainDriverClass {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        //CRUD
        //CREATE
        int id = insertQuery("Bush", "Lily", Date.valueOf("1980-01-04"),
                "bush@yahoo.com", "(408) 893-666");

        System.out.println(String.format("A new candidate with id %d has  been inserted", id));

        //READ
        selectQuery();

        //UPDATE
        updateQuery();

    }

    public static int insertQuery(String lastName, String firstName, Date dob, String email, String phone) {

        ResultSet rs = null;

        int candidateId = 0;

        String sql = "INSERT INTO candidates (first_name, last_name,dob,email,phone)"
                + "VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, dob);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);

            int rowAffected = pstmt.executeUpdate();

            if (rowAffected == 1) {

                //get candidate
                rs = pstmt.getGeneratedKeys();

                if (rs.next()) {

                    candidateId = rs.getInt(1);

                }

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        } finally {

            try {

                if (rs != null) {

                    rs.close();
                }

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }
        }

        return candidateId;

    }

    public static void selectQuery() {

        String sql = "SELECT *  FROM candidates";

        try (Connection conn = DBConnect.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                System.out.println(rs.getString("first_name")
                        + "\t" + rs.getString("last_name")
                        + "\t" + rs.getString("email"));

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

    public static void updateQuery() throws IOException {

        String sqlUpdate = "UPDATE candidates"
                + "SET last_name = ?"
                + "WHERE id = ? ";

        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            String last_Name = "Khan";
            int id = 100;
            pstmt.setInt(1, id);
            pstmt.setString(1, last_Name);

            int rowAffected = pstmt.executeUpdate();
            System.out.println(String.format("Row affected %d", rowAffected));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(MainDriverClass.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
