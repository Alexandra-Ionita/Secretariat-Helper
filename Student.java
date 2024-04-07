package org.example;

import java.util.ArrayList;

public class Student {
  private final String name;
  private double gpa;
  private final ArrayList<Course<?>> preferences = new ArrayList<>();
  private Course<?> assignedCourse;

  public Student(String studentName) {
    this.name = studentName;
  }

  public String getName() {
    return name;
  }

  public double getGpa() {
    return gpa;
  }

  public void setGpa(double gpa) {
    this.gpa = gpa;
  }

  public ArrayList<Course<?>> getPreferences() {
    return preferences;
  }

  public Course<?> getAssignedCourse() {
    return assignedCourse;
  }

  public void setAssignedCourse(Course<?> assignedCourse) {
    this.assignedCourse = assignedCourse;
  }
}
