package org.eric.stainertreeproblem.trees;

import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.util.ArrayList;
import java.util.List;

public class Prim {

    public static void createMinimumSpanningTree(List<TreeApex> apexes) {
        for (int k = 0; k < apexes.size() - 1; k++) {
            List<Distance> minDistances = new ArrayList<>();
            for (int i = 0; i < apexes.size(); i++) { // here
                List<Distance> distances = new ArrayList<>();
                //starting apex should be connected with tree
                if (apexes.get(i).getPreviousApexId() != -1) {
                    for (TreeApex apex : apexes) {
                        //ending apex should not be the same apex
                        if (apex.getPreviousApexId() == -1 && apex.getId() != apexes.get(i).getId()) {
                            double distance = measureDistance(apexes.get(i), apex);
                            Distance currentDistance = new Distance();
                            currentDistance.distanceToParent = distance;
                            currentDistance.startingApexId = apex.getId();
                            currentDistance.endingApexId = apexes.get(i).getId();
                            distances.add(currentDistance);
                        }
                    }
                    minDistances.add(findMinDistance(distances));
                }
            }
            if (minDistances.size() != 0) {
                Distance minDistance = findMinDistance(minDistances);
                for (TreeApex e : apexes) {
                    if (e.getId() == minDistance.endingApexId) {
                        e.setConnectedFurther(true);
                        break;
                    }
                }
                for (TreeApex e : apexes) {
                    if (e.getId() == minDistance.startingApexId) {
                        e.setPreviousApexId(minDistance.endingApexId);
                        e.setDistanceToParent(minDistance.distanceToParent);
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private static Distance findMinDistance(List<Distance> distances) {
        Distance minDistance = new Distance();
        minDistance.distanceToParent = Double.MAX_VALUE;
        for (Distance e : distances) {
            if (e.distanceToParent < minDistance.distanceToParent) {
                minDistance = e;
            }
        }
        return minDistance;
    }

    private static double measureDistance(TreeApex firstTreeApex, TreeApex secondTreeApex) {
        List<Double> coordinates = new ArrayList<>();
        for (int i = 0; i < firstTreeApex.getCoordinates().size(); i++) {
            coordinates.add(Math.abs(secondTreeApex.getCoordinates().get(i) - firstTreeApex.getCoordinates().get(i)));
        }
        double length = 0.0;
        for (double e : coordinates) {
            e = Math.pow(e, 2);
            length += e;
        }
        return length;
    }

    static class Distance {
        double distanceToParent;
        int startingApexId;
        int endingApexId;
    }
}
