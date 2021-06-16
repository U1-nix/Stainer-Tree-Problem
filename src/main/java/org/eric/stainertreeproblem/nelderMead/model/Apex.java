package org.eric.stainertreeproblem.nelderMead.model;

import java.util.ArrayList;
import java.util.List;

public class Apex{
    // mutable
    private final List<Double> coordinates;
    // primitive so immutable
    private double functionValue;

    public Apex(List<Double> coordinates, double functionValue) {
        this.coordinates = coordinates;
        this.functionValue = functionValue;
    }

    // Copy constructor
    public Apex(Apex apex) {
        this.coordinates = new ArrayList<>(apex.getCoordinates());
        this.functionValue = apex.getFunctionValue();
    }

    public double getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(double functionValue) {
        this.functionValue = functionValue;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }
}
