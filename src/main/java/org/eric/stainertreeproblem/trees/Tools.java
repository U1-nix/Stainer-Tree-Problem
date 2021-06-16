package org.eric.stainertreeproblem.trees;

import org.eric.stainertreeproblem.trees.model.TreeApex;

import java.util.List;

public class Tools {

    public static double calculateTreeLength(List<TreeApex> apexes) {
        double sum = 0;
        for (TreeApex e : apexes) {
            if (e.getPreviousApexId() != -2) {
                sum += e.getDistanceToParent();
            }
        }
        return sum;
    }
}