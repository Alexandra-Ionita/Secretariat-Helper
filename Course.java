package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Course<T extends Student> {
  private final String courseName;
  private final int maxEnrollment;
  private double smallestGpa = 0;
  ArrayList<T> enrolledStudents = new ArrayList<>();

  public Course(String courseName, int maxEnrollment) {
    this.courseName = courseName;
    this.maxEnrollment = maxEnrollment;
  }

  public String getCourseName() {
    return courseName;
  }

  public int getMaxEnrollment() {
    return maxEnrollment;
  }

  public ArrayList<T> getEnrolledStudents() {
    return enrolledStudents;
  }

  public double getSmallestGpa() {
    return smallestGpa;
  }

  public void setSmallestGpa(double smallestGpa) {
    this.smallestGpa = smallestGpa;
  }

  void sortEnrolledStudents() {
    Collections.sort(enrolledStudents, Comparator
            .comparing(Student::getName));
  }
}
