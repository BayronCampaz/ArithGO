package edu.icesi.arithgo.model.entity;

import java.io.Serializable;

public class Score implements Serializable {
    private String id;
    private int points;

    public Score() {
    }

    public Score(String id, int points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
