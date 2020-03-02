package com.ml.morse.model;

import java.io.Serializable;

public class BinaryResponseDTO implements Serializable {

    private String text;

    private String morse;

    private BinaryStats spaceStats;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMorse() {
        return morse;
    }

    public void setMorse(String morse) {
        this.morse = morse;
    }

    public BinaryStats getSpaceStats() {
        return spaceStats;
    }

    public void setSpaceStats(BinaryStats spaceStats) {
        this.spaceStats = spaceStats;
    }
}
