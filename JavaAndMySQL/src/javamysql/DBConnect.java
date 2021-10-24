package javamysql;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Student
 */
public class DBConnect {

    /**
     *
     * @return conn
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws IOException, SQLException {

        Connection conn = null;

        try (FileInputStream f = new FileInputStream("db.properties")) {

            Properties pros = new Properties();
            pros.load(f);

            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }

        return conn;

    }

}
