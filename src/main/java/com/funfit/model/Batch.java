package com.funfit.model;

import java.sql.Time;

public class Batch {
    private int id;
    private String name;
    private Time startTime;
    private Time endTime;
    private String description;

    public Batch() {}

    public Batch(int id, String name, Time startTime, Time endTime, String description) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    // getters + setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Time getEndTime() { return endTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

