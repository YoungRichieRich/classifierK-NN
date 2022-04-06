import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Attribute {

    private List<Double> coords;
    private String name;

    public Attribute(String line) {
        String[] splitedLine = line.split(";");
        List<String> lineAsList = new ArrayList<>(Arrays.asList(splitedLine).subList(0, splitedLine.length - 1));
        this.name = splitedLine[splitedLine.length - 1];
        coords = lineAsList.stream().map(Double::parseDouble).collect(Collectors.toList());

    }

    public List<Double> getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }


    public Double get(int i) {
        return coords.get(i);
    }


    @Override
    public String toString() {
        return coords.toString() + " " + name;
    }
}
