package org.eric.stainertreeproblem.nelderMead;

import org.eric.stainertreeproblem.nelderMead.model.Apex;
import org.eric.stainertreeproblem.trees.Minimising;
import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class NelderMead {
    private static final double alpha = 1.0; // default = 1
    private static final double beta = 0.5; // default = 0.5
    private static final double gamma = 2.0; // default = 2
    private static final double epsilon = 0.1; // default = 0.1

    public static List<Double> minimise(List<Double> startingCoordinates, List<TreeApex> apexes) {
        System.out.println("Beginning Nelder Mead's algorithm");
        Apex p0 = new Apex(startingCoordinates, 0.0);

        // create Apexes according to p0
        List<Apex> startingValues = new ArrayList<>(initialize(p0));

        // А - find function values
        for (Apex e : startingValues) {
            e.setFunctionValue(Minimising.minimisingFunction(e.getCoordinates(), apexes));
        }

        // iterator counter
        int it = 0;
        while (true) {
            it++;

            // Б - sort by function value; fh > fg > fl
            startingValues.sort(Comparator.comparing(Apex::getFunctionValue).reversed());
            int maxApex = 0;
            int secondMaxApex = 1;
            int minApex = startingCoordinates.size()-1;

            // B - calculate center of gravity; centerOfGravity; f0
            Apex centerOfGravity = initializeCenterOfGravity(startingValues, apexes, startingValues.get(maxApex).getCoordinates());
            // Г - calculate reflected apex; reflectedApex; fr
            Apex reflectedApex = initializeReflectedApex(centerOfGravity.getCoordinates(), startingValues.get(maxApex).getCoordinates(), apexes);

            // Д - compare fr and fl
            if (reflectedApex.getFunctionValue() < startingValues.get(minApex).getFunctionValue()) {
                // fr < fl

                // Д.1) - calculate strained apex; strainedApex; fe
                Apex strainedApex = initializeStrainedApex(reflectedApex.getCoordinates(), centerOfGravity.getCoordinates(), apexes);

                // compare fe and fl
                if (strainedApex.getFunctionValue() < startingValues.get(minApex).getFunctionValue()) {
                    // fe < fl

                    // a) - replace xh with xe
                    startingValues.set(maxApex, new Apex(strainedApex));

                    if (checkPrecision(startingValues)) {
                        // stop
                        break;
                    }
                    // else --> В
                } else {
                    // fe >= fl

                    // б) - replace xh with xr
                    startingValues.set(maxApex, new Apex(reflectedApex));

                    if (checkPrecision(startingValues)) {
                        // stop
                        break;
                    }
                    // else --> B
                }
            } else {
                // fr > fl

                // compare fr and fg
                if (reflectedApex.getFunctionValue() <= startingValues.get(secondMaxApex).getFunctionValue()) {
                    // fr <= fg

                    // Д.2) - replace xh with xr
                    startingValues.set(maxApex, new Apex(reflectedApex));

                    if (checkPrecision(startingValues)) {
                        // stop
                        break;
                    }
                    // else --> B
                } else {
                    // E - compare fr and fh
                    if (reflectedApex.getFunctionValue() < startingValues.get(maxApex).getFunctionValue()) {
                        // fr < fh

                        // E.1) - replace xh with xr
                        startingValues.set(maxApex, new Apex(reflectedApex));
                    }

                    // fr >= fh
                    // E.2) - calculate compressed apex; compressedApex; fc
                    Apex compressedApex = initializeCompressedApex(startingValues.get(maxApex).getCoordinates(), centerOfGravity.getCoordinates(), apexes);

                    // Ж - compare fc and fh
                    if (compressedApex.getFunctionValue() <= startingValues.get(maxApex).getFunctionValue()) {
                        // fc <= fh

                        // Ж.1) - replace xh with xc;
                        startingValues.set(maxApex, new Apex(compressedApex));

                        if (checkPrecision(startingValues)) {
                            // stop
                            break;
                        }
                        // else --> Б
                    } else {
                        // fc > fh
                        // З - double downsizing
                        for (int i = 0; i < startingValues.size(); i++) {
                            if (i != minApex) {
                                startingValues.set(i, downsize(startingValues.get(i), new Apex(startingValues.get(minApex))));
                                startingValues.get(i).setFunctionValue(Minimising.minimisingFunction(startingValues.get(i).getCoordinates(), apexes));
                            }
                        }

                        startingValues.get(minApex).setFunctionValue(Minimising.minimisingFunction(startingValues.get(minApex).getCoordinates(), apexes));

                        if (checkPrecision(startingValues)) {
                            // stop
                            break;
                        }
                        // else --> В
                    }
                }
            }
        }

        System.out.println("Iterations: " + it);

        // return minApex;
        System.out.println(startingValues.get(startingValues.size() - 1).getFunctionValue());
        return startingValues.get(startingValues.size() - 1).getCoordinates();
    }

    private static List<Apex> initialize(Apex p0) {
        List<Apex> startingValues = new LinkedList<>();
        startingValues.add(p0);
        for (int i = 1; i <= p0.getCoordinates().size(); i++) {
            Apex p = new Apex(p0);
            double x = p.getCoordinates().get(i - 1);
            p.getCoordinates().remove(i - 1);
            x++;
            p.getCoordinates().add(i - 1, x);
            startingValues.add(p);
        }
        return startingValues;
    }

    private static Apex initializeCenterOfGravity(List<Apex> startingValues, List<TreeApex> apexes, List<Double> maxApexCoordinates) {
        Apex centerOfGravity = new Apex(findCenterOfGravity(startingValues, maxApexCoordinates),
                0.0);
        centerOfGravity.setFunctionValue(Minimising.minimisingFunction(centerOfGravity.getCoordinates(), apexes));

        System.out.println("Center Of Gravity");
        System.out.println("Center Of Gravity Coordinates: " + centerOfGravity.getCoordinates());
        System.out.println("Max Apex Coordinates: " + maxApexCoordinates);
        System.out.println("Tree length: " + centerOfGravity.getFunctionValue());
        System.out.println();

        return centerOfGravity;
    }

    private static List<Double> findCenterOfGravity(List<Apex> startingValues, List<Double> maxApexCoordinates) {
        List<Double> centerOfGravityCoordinates = new ArrayList<>();
        // i - coordinate number
        for (int i = 0; i < startingValues.get(0).getCoordinates().size(); i++) {
            // sum of all i coordinates
            double sum = 0;
            for (Apex e : startingValues) {
                sum += e.getCoordinates().get(i);
            }
            // subtraction of i coordinate of highest simplex
            sum -= maxApexCoordinates.get(i);
            centerOfGravityCoordinates.add(sum / (startingValues.size() - 1));
        }
        return centerOfGravityCoordinates;
    }

    private static Apex initializeReflectedApex(List<Double> centerOfGravityCoordinates, List<Double> maxApexCoordinates, List<TreeApex> apexes) {
        Apex reflectedApex = new Apex(reflect(centerOfGravityCoordinates, maxApexCoordinates),
                0.0);
        reflectedApex.setFunctionValue(Minimising.minimisingFunction(reflectedApex.getCoordinates(), apexes));

        System.out.println("Reflected Apex");
        System.out.println("Reflected Apex Coordinates: " + reflectedApex.getCoordinates());
        System.out.println("Center Of Gravity Coordinates: " + centerOfGravityCoordinates);
        System.out.println("Max Apex Coordinates: " + maxApexCoordinates);
        System.out.println("Tree length: " + reflectedApex.getFunctionValue());
        System.out.println();

        return reflectedApex;
    }

    private static List<Double> reflect(List<Double> centerOfGravityCoordinates, List<Double> maxApexCoordinates) {
        List<Double> reflectedCoordinates = new ArrayList<>();
        for (int i = 0; i < centerOfGravityCoordinates.size(); i++) {
            double result = (1 + alpha) * centerOfGravityCoordinates.get(i) - alpha * maxApexCoordinates.get(i);
            reflectedCoordinates.add(result);
        }
        return reflectedCoordinates;
    }

    private static Apex initializeStrainedApex(List<Double> reflectedApexCoordinates, List<Double> centerOfGravityCoordinates, List<TreeApex> apexes) {
        Apex strainedApex = new Apex(strain(reflectedApexCoordinates, centerOfGravityCoordinates), 0.00);
        strainedApex.setFunctionValue(Minimising.minimisingFunction(strainedApex.getCoordinates(), apexes));

        System.out.println("Strained Apex");
        System.out.println("Strained Apex Coordinates: " + strainedApex.getCoordinates());
        System.out.println("Reflected Apex Coordinates: " + reflectedApexCoordinates);
        System.out.println("Center Of Gravity Coordinates: " + centerOfGravityCoordinates);
        System.out.println("Tree length: " + strainedApex.getFunctionValue());
        System.out.println();

        return strainedApex;
    }

    private static List<Double> strain(List<Double> reflectedApexCoordinates, List<Double> centerOfGravityCoordinates) {
        List<Double> strainedCoordinates = new ArrayList<>();
        for (int i = 0; i < centerOfGravityCoordinates.size(); i++) {
            double result = gamma * reflectedApexCoordinates.get(i) + (1 - gamma) * centerOfGravityCoordinates.get(i);
            strainedCoordinates.add(result);
        }
        return strainedCoordinates;
    }

    private static Apex initializeCompressedApex(List<Double> maxApexCoordinates, List<Double> centerOfGravityCoordinates, List<TreeApex> apexes) {
        Apex compressedApex = new Apex(compress(maxApexCoordinates, centerOfGravityCoordinates), 0.0);
        compressedApex.setFunctionValue(Minimising.minimisingFunction(compressedApex.getCoordinates(), apexes));

        System.out.println("Compressed Apex");
        System.out.println("Compressed Apex Coordinates" + compressedApex.getCoordinates());
        System.out.println("Max Apex Coordinates: " + maxApexCoordinates);
        System.out.println("Center Of Gravity Coordinates: " + centerOfGravityCoordinates);
        System.out.println("Tree length: " + compressedApex.getFunctionValue());
        System.out.println();

        return compressedApex;
    }

    private static List<Double> compress(List<Double> maxApexCoordinates, List<Double> centerOfGravityCoordinates) {
        List<Double> compressedCoordinates = new ArrayList<>();
        for (int i = 0; i < maxApexCoordinates.size(); i++) {
            double result = beta * maxApexCoordinates.get(i) + (1 - beta) * centerOfGravityCoordinates.get(i);
            compressedCoordinates.add(result);
        }
        return compressedCoordinates;
    }

    private static Apex downsize(Apex otherApex, Apex minApex) {
        Apex result = new Apex(new ArrayList<>(), 0.0);
        for (int i = 0; i < otherApex.getCoordinates().size(); i++) {
            double xi = (otherApex.getCoordinates().get(i) + minApex.getCoordinates().get(i)) / 2;
            result.getCoordinates().add(xi);
        }
        return result;
    }

    private static boolean checkPrecision(List<Apex> values) {
        for (int j = 0; j < values.size() - 1; j++) {
            for (int k = j + 1; k < values.size(); k++) {
                double distance = checkTwoApexes(values.get(j), values.get(k));
                if (distance > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double checkTwoApexes(Apex firstApex, Apex secondApex) {
        double distance = 0.0;
        for (int i = 0; i < firstApex.getCoordinates().size(); i++) {
            distance += Math.pow(firstApex.getCoordinates().get(i) - secondApex.getCoordinates().get(i), 2);
        }
        return distance;
    }
}
