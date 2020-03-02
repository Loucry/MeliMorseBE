package com.ml.morse.model;

import java.io.Serializable;

public class RequestDTO implements Serializable {

    private String text;

    private BinaryStats spaceStats;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BinaryStats getSpaceStats() {
        return spaceStats;
    }

    public void setSpaceStats(BinaryStats spaceStats) {
        this.spaceStats = spaceStats;
    }

    public boolean areSpaceStatsValid() {
        return spaceStats != null && spaceStats.getMinLenght() != null && spaceStats.getMaxLenght() != null;
    }
}
