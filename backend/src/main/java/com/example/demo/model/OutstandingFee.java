package com.example.demo.model;

public class OutstandingFee {
    private Integer studentID;
    private String name;
    private Integer outstandingAmount;

    // Getters and setters
    public Integer getStudentID() { return studentID; }
    public void setStudentID(Integer studentID) { this.studentID = studentID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getOutstandingAmount() { return outstandingAmount; }
    public void setOutstandingAmount(Integer outstandingAmount) { this.outstandingAmount = outstandingAmount; }
}