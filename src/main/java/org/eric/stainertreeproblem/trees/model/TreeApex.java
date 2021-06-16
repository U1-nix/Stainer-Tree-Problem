package org.eric.stainertreeproblem.trees.model;

import java.util.ArrayList;
import java.util.List;

public class TreeApex {
    private int id;
    private List<Double> coordinates;
    private int previousApexId;
    private boolean connectedFurther;
    private double distanceToParent;
    private boolean isAdditional;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isConnectedFurther() {
        return connectedFurther;
    }

    public void setConnectedFurther(boolean connectedFurther) {
        this.connectedFurther = connectedFurther;
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

    public void setAdditional(boolean additional) {
        isAdditional = additional;
    }

    @Override
    public TreeApex clone() {
        TreeApex newApex = new TreeApex();
        newApex.id = this.id;
        newApex.coordinates = new ArrayList<>(this.coordinates);
        newApex.previousApexId = this.previousApexId;
        newApex.connectedFurther = this.connectedFurther;
        newApex.distanceToParent = this.distanceToParent;
        newApex.isAdditional = this.isAdditional;
        return newApex;
    }
}
