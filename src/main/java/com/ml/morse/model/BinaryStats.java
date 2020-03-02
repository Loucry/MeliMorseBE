package com.ml.morse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Iterables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BinaryStats implements Serializable {

    @JsonIgnore
    private List<Integer> lengths = new ArrayList<>();
    private Integer minLenght;
    private Integer maxLenght;

    public List<Integer> getLengths() {
        return lengths;
    }

    public void setLengths(List<Integer> lengths) {
        this.lengths = lengths;
    }

    public Integer getMinLenght() {
        return minLenght;
    }

    public void setMinLenght(Integer minLenght) {
        this.minLenght = minLenght;
    }

    public Integer getMaxLenght() {
        return maxLenght;
    }

    public void setMaxLenght(Integer maxLenght) {
        this.maxLenght = maxLenght;
    }

    public BinaryStats() {
        this.minLenght = 1;
        this.maxLenght = 8;
    }

    @Override
    public String toString() {
        return "{" +
                "lengths=" + Iterables.toString(lengths) +
                ", minLenght=" + minLenght +
                ", maxLengt=" + maxLenght +
                '}';
    }
}
