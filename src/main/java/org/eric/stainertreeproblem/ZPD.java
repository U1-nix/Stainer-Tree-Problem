package org.eric.stainertreeproblem;

import org.eric.stainertreeproblem.nelderMead.NelderMead;
import org.eric.stainertreeproblem.trees.Debug;
import org.eric.stainertreeproblem.trees.Tools;
import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ZPD {
    public static void main(String[] args) {
        double length = Double.MAX_VALUE;
        List<TreeApex> minimalTree = new ArrayList<>();
        List<Double> bestCoordinatesForAdditionalTreeApexes = new ArrayList<>();
        long minimalElapsedTime = Long.MAX_VALUE;

        for (int i = 0; i < 5; i++) {
            List<TreeApex> apexes = new ArrayList<>(initializeTreeApexes());
            Debug.showTreeApexes(apexes);

            long startTime = System.nanoTime();
            long currentElapsedTime;

            bestCoordinatesForAdditionalTreeApexes = new ArrayList<>(NelderMead.minimise(prepareCoordinates(apexes), apexes));

            currentElapsedTime = System.nanoTime() - startTime;
            if (minimalElapsedTime > currentElapsedTime) {
                minimalElapsedTime = currentElapsedTime;
            }
            double currentLength = Tools.calculateTreeLength(apexes);
            if (length > currentLength) {
                length = currentLength;
                minimalTree.clear();
                for (TreeApex apex : apexes) {
                    minimalTree.add(new TreeApex(apex));
                }
            }
        }

        System.out.println("Best Coordinates" + bestCoordinatesForAdditionalTreeApexes);
        System.out.println("Exit values: ");
        Debug.showTreeApexes(minimalTree);
        System.out.println("Shortest tree length: " + length);
        minimalElapsedTime = TimeUnit.SECONDS.convert(minimalElapsedTime, TimeUnit.NANOSECONDS);
        System.out.println("Minimal elapsed time " + minimalElapsedTime + " second\\s");
    }

    public static List<TreeApex> initializeTreeApexes() {
        List<TreeApex> apexes = new ArrayList<>();
//         ZPD.randomlyInitializeMainTreeApexes();
//         int idCounter = initializeMainTreeApexes(apexes);
        int idCounter = initializeMainTreeApexesFromFile(apexes);
        initializeAdditionalTreeApexes(apexes, idCounter);
        return apexes;
    }

    private static void randomlyInitializeMainTreeApexes() {
        File file = new File("C:\\Users\\ercep\\Desktop\\ZPDStainerTreeProblem\\src\\main\\java\\in.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            int numberOfMainTreeApexes = 20;
            printWriter.print(numberOfMainTreeApexes + "\n");

            for (int i = 1; i <= numberOfMainTreeApexes; i++) {
                double rangeMinX = -150.0;
                double rangeMaxX = 150.0;
                double rangeMinY = -150.0;
                double rangeMaxY = 150.0;
                Random r = new Random();
                double randomValueX = rangeMinX + (rangeMaxX - rangeMinX) * r.nextDouble();
                double randomValueY = rangeMinY + (rangeMaxY - rangeMinY) * r.nextDouble();

                printWriter.print(randomValueX + "\n");
                if (numberOfMainTreeApexes != i) {
                    printWriter.print(randomValueY + "\n");
                } else {
                    printWriter.print(randomValueY);
                }
            }


            printWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static int initializeMainTreeApexesFromFile(List<TreeApex> apexes) {
        int idCounter = 0;
        File file = new File("C:\\Users\\ercep\\Desktop\\ZPDStainerTreeProblem\\src\\main\\java\\in.txt");
        if (file.exists())
            System.out.println("Exists");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int numberOfMainApexes = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < numberOfMainApexes; i++) {
                TreeApex treeApex = new TreeApex(i, false);
                if (i == 0) {
                    // -2 means first tree apex
                    treeApex.setPreviousApexId(-2);
                } else {
                    treeApex.setPreviousApexId(-1);
                }
                treeApex.setDistanceToParent(Double.MAX_VALUE);
                List<Double> coordinates = new ArrayList<>();
                coordinates.add(Double.parseDouble(bufferedReader.readLine()));
                coordinates.add(Double.parseDouble(bufferedReader.readLine()));
                treeApex.setCoordinates(coordinates);
                apexes.add(treeApex);
                idCounter++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Problem with file");
            System.out.println(e);
        }
        return idCounter;
    }

    private static int initializeMainTreeApexes(List<TreeApex> apexes) {
        Scanner scanner = new Scanner(System.in);
        int idCounter = 0;
        int numberOfMainApexes = scanner.nextInt();
        for (int i = 0; i < numberOfMainApexes; i++) {
            TreeApex treeApex = new TreeApex(i, false);
            // -1 means no next tree apex
            if (i == 0) {
                // -2 means first tree apex
                treeApex.setPreviousApexId(-2);
            } else {
                treeApex.setPreviousApexId(-1);
            }
            treeApex.setDistanceToParent(Double.MAX_VALUE);
            List<Double> coordinates = new ArrayList<>();
            // userInput - coordinate in String format
            String userInput = scanner.next();
            double coordinate = Double.parseDouble(userInput);
            coordinates.add(coordinate);
            userInput = scanner.next();
            coordinate = Double.parseDouble(userInput);
            coordinates.add(coordinate);
            treeApex.setCoordinates(coordinates);
            apexes.add(treeApex);
            idCounter++;
        }
        return idCounter;
    }

    private static void initializeAdditionalTreeApexes(List<TreeApex> apexes, int idCounter) {
        Scanner scanner = new Scanner(System.in);
        int numberOfAdditionalApexes = scanner.nextInt();
//        int numberOfAdditionalApexes = 15;
        for (int i = 0; i < numberOfAdditionalApexes; i++) {
            TreeApex treeApex = new TreeApex(i, true);
            treeApex.setPreviousApexId(-1);
            treeApex.setDistanceToParent(Double.MAX_VALUE);
            List<Double> coordinates = new ArrayList<>();

            double rangeMinX = -150.0;
            double rangeMaxX = 150.0;
            double rangeMinY = -150.0;
            double rangeMaxY = 150.0;
            Random r = new Random();
            double randomValueX = rangeMinX + (rangeMaxX - rangeMinX) * r.nextDouble();
            double randomValueY = rangeMinY + (rangeMaxY - rangeMinY) * r.nextDouble();

            coordinates.add(i + randomValueX);
            coordinates.add(i + randomValueY);
            treeApex.setCoordinates(coordinates);
            apexes.add(treeApex);
            idCounter++;
        }
    }

    public static List<Double> prepareCoordinates(List<TreeApex> apexes) {
        List<Double> coordinates = new ArrayList<>();
        for (TreeApex e : apexes) {
            if (e.isAdditional()) {
                coordinates.add(e.getCoordinates().get(0));
                coordinates.add(e.getCoordinates().get(1));
            }
        }
        return coordinates;
    }
}
