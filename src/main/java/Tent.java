import com.google.gson.annotations.SerializedName;

/**
 * Created by Gregory on 2017-10-26.
 */

public class Tent  {
    @SerializedName("Title")
    public String Title;
    @SerializedName("Longitude")
    public String Longitude;
    @SerializedName("Latitude")
    public String Latitude;

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

    public Tent() {}

    public Tent(String title, String longitude, String latitude) {
        Title = title;
        Longitude = longitude;
        Latitude = latitude;
    }





}
