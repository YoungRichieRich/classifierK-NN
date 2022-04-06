public class Distance {

    private final Attribute test;
    private final Attribute traning;
    private double distance;

    public Distance(Attribute test, Attribute traning) {
        this.test = test;
        this.traning = traning;
        calcDistance();
    }

    public Attribute getTest() {
        return test;
    }

    public Attribute getTraning() {
        return traning;
    }

    public Double getDistance() {
        return distance;
    }

    public void calcDistance() {

        for (int i = 0; i < test.getCoords().size(); i++) {
            distance += Math.pow(test.get(i) - traning.get(i), 2);
        }
        this.distance = Math.sqrt(distance);

    }

    @Override
    public String toString() {
        return distance+" " + test + " " + traning;
    }
}
