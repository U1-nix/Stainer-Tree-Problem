package org.eric.stainertreeproblem.trees.model;

import java.util.ArrayList;
import java.util.List;

public class TreeApex {
    private final int id;
    private List<Double> coordinates;
    private int previousApexId;
    private double distanceToParent;
    private final boolean isAdditional;

    public TreeApex(int id, boolean isAdditional) {
        this.id = id;
        this.isAdditional = isAdditional;
    }

    // copy constructor
    public TreeApex(TreeApex treeApex) {
        this.id = treeApex.getId();
        this.coordinates = new ArrayList<>(treeApex.getCoordinates());
        this.previousApexId = treeApex.getPreviousApexId();
        this.distanceToParent = treeApex.getDistanceToParent();
        this.isAdditional = treeApex.isAdditional();
    }

    public int getId() {
        return id;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public int getPreviousApexId() {
        return previousApexId;
    }

    public void setPreviousApexId(int previousApexId) {
        this.previousApexId = previousApexId;
    }

    public double getDistanceToParent() {
        return distanceToParent;
    }

    public void setDistanceToParent(double distanceToParent) {
        this.distanceToParent = distanceToParent;
    }

    public boolean isAdditional() {
        return isAdditional;
    }
}
