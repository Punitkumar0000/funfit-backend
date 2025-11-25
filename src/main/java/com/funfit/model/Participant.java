package com.funfit.model;

import java.sql.Date;

public class Participant {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Integer batchId; // nullable
    private Date enrolledDate;

    public Participant() {}

    public Participant(int id, String name, String email, String phone, Integer batchId, Date enrolledDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.batchId = batchId;
        this.enrolledDate = enrolledDate;
    }

    // getters + setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getBatchId() { return batchId; }
    public void setBatchId(Integer batchId) { this.batchId = batchId; }

    public Date getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(Date enrolledDate) { this.enrolledDate = enrolledDate; }
}
