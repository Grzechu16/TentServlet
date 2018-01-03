import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gregory on 2018-01-02.
 */
public class TentServlet extends HttpServlet {
    String Title = null;
    String Longitude = null;
    String Latitude = null;
    List<Tent> Tent = new ArrayList<>();
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
                    "jdbc:mysql://api.dbudziasz.pl/23040354_tent?useSSL=false", "23040354_tent", "-t-Qa5w*G,!F"); // <== Check!
            stmt = conn.createStatement();

            String sqlStr = "select * from Tents ";
            ResultSet rset = stmt.executeQuery(sqlStr);

            int count = 0;
            while(rset.next()) {

                Title = rset.getString("Title");
                Longitude = String.valueOf(rset.getDouble("Longitude"));
                Latitude = String.valueOf(rset.getDouble("Latitude"));
                Tent tent = new Tent(Title,Longitude,Latitude);
                Tent.add(tent);
                count++;
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
}