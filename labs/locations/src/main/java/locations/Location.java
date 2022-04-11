package locations;

import java.util.Objects;

public class Location {

    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        //Kivételkezelés és timeout tesztelése
        if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180) {
            throw new IllegalArgumentException("Out of this dimension");
        }
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Tesztesetek életciklusa
    public boolean isOnEquator() {
        return latitude == 0;
    }

    //Tesztesetek életciklusa
    public boolean isOnPrimeMeridian() {
        return longitude == 0;
    }

    //Assert 2.
    public double distanceFrom(Location otherLocation) {
        int earthRadius = 6371;
        double latitudeDifference = Math.toRadians(otherLocation.getLatitude() - this.latitude);
        double longitudeDifference = Math.toRadians(otherLocation.getLongitude() - this.longitude);
        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.cos(Math.toRadians(this.latitude))
                * Math.cos(Math.toRadians(otherLocation.getLatitude())) * Math.pow(Math.sin(longitudeDifference / 2), 2);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(earthRadius * b * 10.0) / 10.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //Assert

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
