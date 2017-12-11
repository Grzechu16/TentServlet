
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetTent extends HttpServlet {
    List<Tent> tents = new ArrayList<>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
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
            while (rset.next()) {
                String name = rset.getString("Name");
                String longitude = rset.getString("Longitude");
                String latitude = rset.getString("Latitude");
                tents.add(new Tent(name, longitude, latitude));
                out.println(new Gson().toJson(tents));
                count++;
            }

    } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}