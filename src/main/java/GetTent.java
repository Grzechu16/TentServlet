import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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

        Connection conn = null;
        List<Tent> tents = new ArrayList<Tent>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://api.dbudziasz.pl/23040354_tent?useSSL=false", "23040354_tent", "-t-Qa5w*G,!F");
            // 1. get received JSON data from request
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String json = "";
            if (br != null) {
                json = br.readLine();
            }

            // 2. initiate jackson mapper
            ObjectMapper mapper = new ObjectMapper();

            // 3. Convert received JSON to Article
            Tent tent = mapper.readValue(json, Tent.class);

            // 4. Set response type to JSON
            response.setContentType("application/json");

            // 5. Add article to List<Article>
            tents.add(tent);
            for (int i = 0; i < tents.size(); i++) {


                // 6. Send List<Article> as JSON to client
                //mapper.writeValue(response.getOutputStream(), tents);

                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Tents (Title, Longitude, Latitude VALUES (?,?,?)");
                preparedStatement.setString(1, tents.get(i).getTitle());
                preparedStatement.setString(2, tents.get(i).getLongitude());
                preparedStatement.setString(3, tents.get(i).getLatitude());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}