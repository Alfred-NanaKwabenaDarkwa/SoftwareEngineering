package com.example.demo.model;

public class LecturerCourse {
    private String courseCode;
    private String lecturerName;
    private Integer courseTaId;
    private Integer creditHours;

    // Getters and setters
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getLecturerName() { return lecturerName; }
    public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }
    
    public Integer getCourseTaId() { return courseTaId; }
    public void setCourseTaId(Integer courseTaId) { this.courseTaId = courseTaId; }
    
    public Integer getCreditHours() { return creditHours; }
    public void setCreditHours(Integer creditHours) { this.creditHours = creditHours; }
} 