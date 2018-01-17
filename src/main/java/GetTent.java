import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.xml.internal.bind.v2.TODO;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gregory on 2018-01-02.
 */
public class GetTent extends HttpServlet {
    String Title = null;
    String Longitude = null;
    String Latitude = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://api.dbudziasz.pl/23040354_tent?useSSL=false", "23040354_tent", "-t-Qa5w*G,!F");
            stmt = conn.createStatement();

            String sqlStr = "select * from Tents ";
            ResultSet rset = stmt.executeQuery(sqlStr);
            List<Tent> Tent = new ArrayList<>();
            //int count = 0;

            while (rset.next()) {

                Title = rset.getString("Title");
                Longitude = String.valueOf(rset.getDouble("Longitude"));
                Latitude = String.valueOf(rset.getDouble("Latitude"));
                Tent tent = new Tent(Title, Longitude, Latitude);
                Tent.add(tent);
                // count++;
            }
            out.print(new Gson().toJson(Tent));

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {

                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        String title = request.getParameter("Title");
        String longitude = request.getParameter("Longitude");
        String latitude = request.getParameter("Latitude");

        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://api.dbudziasz.pl/23040354_tent?useSSL=false", "23040354_tent", "-t-Qa5w*G,!F");

            response.setContentType("application/json");

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Tents (Title, Longitude, Latitude) VALUES (?,?,?)");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, longitude);
            preparedStatement.setString(3, latitude);

            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //TODO unsupress it!
            out.close();
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}