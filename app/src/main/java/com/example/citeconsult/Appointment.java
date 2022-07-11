package com.example.citeconsult;

public class Appointment {
    String key, day, time, student, instructor,status;

    public Appointment() {

    }

    public Appointment(String key, String day, String time, String student, String instructor, String status) {
        this.key = key;
        this.day = day;
        this.time = time;
        this.student = student;
        this.instructor = instructor;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
