package org.eric.stainertreeproblem.trees;

import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.util.ArrayList;
import java.util.List;

public class Minimising {

    public static double minimisingFunction(List<Double> coordinates, List<TreeApex> apexes) {
        int size = apexes.get(0).getCoordinates().size();
        int startingValue = 0;
        for (TreeApex e : apexes) {
            if (e.isAdditional()) {
                List<Double> coords = new ArrayList<>();
                for (int i = startingValue; i < size + startingValue; i++) {
                    coords.add(coordinates.get(i));
                    //coordinates.remove(i);
                }
                e.setCoordinates(coords);
                startingValue += size;
            }
        }
        clearAllConnections(apexes);
        Prim.createMinimumSpanningTree(apexes);
        return Tools.calculateTreeLength(apexes);
    }

    public static void clearAllConnections(List<TreeApex> apexes) {
        for (TreeApex e : apexes) {
            if (e.getPreviousApexId() != -2) {
                e.setPreviousApexId(-1);
            }
            e.setDistanceToParent(Double.MAX_VALUE);
        }
    }
}
