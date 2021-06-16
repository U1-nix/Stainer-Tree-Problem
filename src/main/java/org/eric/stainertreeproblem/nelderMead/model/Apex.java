package org.eric.stainertreeproblem.nelderMead.model;

import java.util.ArrayList;
import java.util.List;

public class Apex implements Cloneable {
    private List<Double> coordinates;
    private double functionValue;

    public Apex(List<Double> coordinates, double functionValue) {
        this.coordinates = coordinates;
        this.functionValue = functionValue;
    }

    @Override
    public Apex clone() {
        List<Double> coordinates = new ArrayList<>(this.getCoordinates());
        return new Apex(coordinates, this.getFunctionValue());
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

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
