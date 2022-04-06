import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static double correctAnswers = 0;
    static int option;

    public static void main(String[] args) {

        int k = 30;
        Scanner scan = new Scanner(System.in);

        System.out.println("========================");
        System.out.println("== Klasyfikator k-NN  ==");
        System.out.println("========================\n");

        System.out.println("Własne pliki czy domyślne?");
        System.out.println("WŁASNE   ----> 1");
        System.out.println("DOMYŚLNE ----> 2");
        int fileOption = scan.nextInt();
        scan.nextLine();
        String trainingFileName = "trainingFile.txt";
        String testFileName = "testFile.txt";

        if (fileOption == 1) {
            System.out.println("Podaj ścieżke do pliku TRENINGOWEGO");
            trainingFileName = scan.nextLine();
            System.out.println("Podaj ścieżke do pliku TESTOWEGO");
            testFileName = scan.nextLine();
        }


        List<String> trainingStrings = readFile(trainingFileName);
        List<Attribute> trainingList = new ArrayList<>();

        trainingStrings.forEach(s -> trainingList.add(new Attribute(s)));
        Scanner scanner = new Scanner(System.in);


        System.out.println("\n========================");
        System.out.print("Podaj parametr K:");
        k = scanner.nextInt();
        System.out.println("========================");
        scanner.nextLine();
        System.out.println("\n========================");
        System.out.println("Wczytaj z pliku ----> 1");
        System.out.println("Podaj ręcznie   ----> 2");
        System.out.println("========================");
        System.out.print("Opcja: ");
        option = scanner.nextInt();

        scanner.nextLine();

        switch (option) {
            case 1 -> {
                List<String> testStrings = readFile(testFileName);
                List<Attribute> testList = new ArrayList<>();
                testStrings.forEach(s -> testList.add(new Attribute(s)));
                doKnnAlg(k, trainingList, testList);
            }
            case 2 -> {

                System.out.println("\n========================");
                System.out.println("Podaj swój wektor [x;x;x;x]");
                System.out.println("Koniec programu ----> exit ");
                System.out.println("========================");
                Scanner scanner1 = new Scanner(System.in);
                while (true) {
                    List<Attribute> testInput = new ArrayList<>();
                    System.out.print("Wektor: ");
                    String line = scanner1.nextLine();
                    if (line.equals("exit")) break;
                    testInput.add(new Attribute(line));
                    doKnnAlg(k, trainingList, testInput);
                    System.out.println("\n========================");

                }

            }

        }
    }

    private static void doKnnAlg(int k, List<Attribute> trainingList, List<Attribute> testList) {
        for (Attribute testAtt : testList) {

            List<Distance> distances = new ArrayList<>();

            for (Attribute treningAtt : trainingList) {
                distances.add(new Distance(testAtt, treningAtt));
            }
            sort(distances);
            Map<String, Integer> hashMap = new LinkedHashMap<>();

            for (int i = 0; i < k; i++) {
                String attributeName = distances.get(i).getTraning().getName();
                if (!hashMap.containsKey(attributeName)) {
                    hashMap.put(attributeName, 1);
                } else {
                    int value = hashMap.get(attributeName) + 1;
                    hashMap.replace(attributeName, value);
                }
            }
            Map.Entry<String, Integer> maxEntry = null;
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            assert maxEntry != null;
            if (option == 2) {
                hashMap.forEach((key, value) -> System.out.println(key + " " + value));
            } else if (testAtt.getName().equals(maxEntry.getKey())) {
                correctAnswers++;
            }
            //hashMap.forEach((key, value) -> System.out.println(key + " " + value));
        }
        if (option == 1) {
            System.out.println("Accuracy: " + correctAnswers / testList.size() * 100 + "%");
        }

    }

    public static List<String> readFile(String fileName) {
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void sort(List<Distance> distances) {
        distances.sort((o1, o2) -> {
            if (o1.getDistance() < o2.getDistance()) {
                return -1;
            } else if (o1.getDistance().equals(o2.getDistance())) {
                return 0;
            } else {
                return 1;
            }
        });
    }


}
