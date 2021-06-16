package org.eric.stainertreeproblem.trees;

import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.util.List;

public class Debug {
    public static void showTreeApexes(List<TreeApex> apexes) {
        for (TreeApex e : apexes) {
            System.out.println("ID: " + e.getId());
            System.out.println("Is additional: " + e.isAdditional());
            System.out.println("Coordinates: " + e.getCoordinates());
            System.out.println("Previous apex ID: " + e.getPreviousApexId());
            System.out.println("Distance to parent: " + e.getDistanceToParent());
            System.out.println();
        }
    }
}
