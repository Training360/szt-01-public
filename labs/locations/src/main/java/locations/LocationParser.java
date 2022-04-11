package locations;

public class LocationParser {

    //Bevezetés a JUnit használatába
    public Location parse(String text) {
        String[] split = text.split(",");
        try {
            return new Location(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        } catch (Exception e) {
            throw new IllegalArgumentException("Something is wrong with parameter text.");
        }
    }
}