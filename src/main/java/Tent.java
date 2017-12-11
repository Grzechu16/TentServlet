/**
 * Created by Gregory on 2017-12-11.
 */
public class Tent {

    private String Title;
    private String Longitude;
    private String Latitude;

    public Tent() {
    }

    @Override
    public String toString() {
        return "Tent{" +
                "Title='" + Title + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                '}';
    }

    public Tent(String Name, String Longitude, String Latitude) {
        Name = Name;
        Longitude = Longitude;
        Latitude = Latitude;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
