package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * The type Secretariat.
 */
public class Secretariat {
  private final ArrayList<Student> students = new ArrayList<>();
  private final ArrayList<Course<?>> courses = new ArrayList<>();

  /**
   * Already exists boolean. It checks if a student already exists.
   *
   * @param name the name
   * @return the boolean
   * @throws IllegalArgumentException the illegal argument exception
   */
  private boolean alreadyExists(String name) throws IllegalArgumentException {
    for (Student s : students) {
      if (s.getName().equals(name)) {
        throw new IllegalArgumentException("Student duplicat: " + name);
      }
    }
    return true;
  }

  /**
   * Add student. It adds a student to the list of students and attributes him a degree.
   *
   * @param degree the degree
   * @param name   the name
   */
  void addStudent(String degree, String name) {
    if (degree.equals("licenta")) {
      if (alreadyExists(name)) {
        BachelorStudent student = new BachelorStudent(name);
        students.add(student);
      }

    } else if (degree.equals("master")) {
      if (alreadyExists(name)) {
        MasterStudent student = new MasterStudent(name);
        students.add(student);
      }
    }

  }

  /**
   * Read grades. It reads the grades from the files.
   *
   * @param testToBeExecuted the test to be executed
   */
  void readGrades(String testToBeExecuted) {
    File folder = new File(testToBeExecuted);
    File[] listOfFiles = folder.listFiles();

    assert listOfFiles != null;

    for (File file : listOfFiles) {
      if (file.getName().startsWith("note_")) {
        try {
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          while (true) {
            String studentAndGrade = br.readLine();
            if (studentAndGrade == null) {
              break;
            }

            String[] parts = studentAndGrade.split(" - ");
            String name = parts[0];
            double grade = Double.parseDouble(parts[1]);

            for (Student student : students) {
              if (student.getName().equals(name)) {
                student.setGpa(grade);
              }
            }
          }

        } catch (IOException e) {
          e.fillInStackTrace();
        }
      }

    }
  }

  /**
   * Write grades. It writes the grades in the output file.
   *
   * @param outputFile the output file
   * @throws IOException the io exception
   */
  void writeGrades(String outputFile) throws IOException {
    FileWriter fw = new FileWriter(outputFile, true);
    BufferedWriter bw = new BufferedWriter(fw);

    try {
      bw.write("***");
      bw.newLine();
    } catch (IOException ignored) {

    }

    sortStudents();

    for (Student student : students) {
      try {
        bw.write(student.getName() + " - " + student.getGpa());
        bw.newLine();
      } catch (IOException ignored) {

      }
    }

    bw.close();
  }

  /**
   * Sort students. It sorts the students by their grades and names.
   */
  private void sortStudents() {
    Collections.sort(students, Comparator
            .comparing(Student::getGpa, Comparator.reverseOrder())
            .thenComparing(Student::getName));
  }

  /**
   * Grade appeal. It changes the grade of a student.
   *
   * @param studentName the student name
   * @param grade       the grade
   */
  void gradeAppeal(String studentName, String grade) {
    for (Student student : students) {
      if (student.getName().equals(studentName)) {
        student.setGpa(Double.parseDouble(grade));
        break;
      }
    }
  }

  /**
   * Add course. It adds a course to the list of courses.
   *
   * @param type          the type
   * @param coursename    the coursename
   * @param maxEnrollment the max enrollment
   */
  void addCourse(String type, String coursename, String maxEnrollment) {
    if (type.equals("licenta")) {
      Course<BachelorStudent> course = new Course<>(coursename, Integer.parseInt(maxEnrollment));
      courses.add(course);
    } else if (type.equals("master")) {
      Course<MasterStudent> course = new Course<>(coursename, Integer.parseInt(maxEnrollment));
      courses.add(course);
    }
  }

  /**
   * Add preferences. It adds the preferences of a student.
   *
   * @param commandParts the command parts
   */
  void addPreferences(String[] commandParts) {
    ArrayList<String> preferredCourses = new ArrayList<>(Arrays.asList(commandParts).subList(2, commandParts.length));

    for (Student student : students) {
      if (student.getName().equals(commandParts[1])) {
        for (String course : preferredCourses) {
          for (Course<?> c : courses) {
            if (c.getCourseName().equals(course)) {
              student.getPreferences().add(c);
            }
          }
        }
        break;
      }
    }
  }

  /**
   * Students allocation. It allocates the students to the courses.
   */
  void studentsAllocation() {
    sortStudents();

    for (Student student : students) {
      if (student instanceof BachelorStudent) {
        for (Course bachelorStudentCourse : student.getPreferences()) {
          if (bachelorStudentCourse.getEnrolledStudents().size() < bachelorStudentCourse.getMaxEnrollment()) {
            bachelorStudentCourse.setSmallestGpa(student.getGpa());
            bachelorStudentCourse.getEnrolledStudents().add((BachelorStudent) student);
            student.setAssignedCourse(bachelorStudentCourse);
            break;
          } else {
            if (student.getGpa() == bachelorStudentCourse.getSmallestGpa()) {
              bachelorStudentCourse.getEnrolledStudents().add((BachelorStudent) student);
              student.setAssignedCourse(bachelorStudentCourse);
              break;
            }
          }
        }
      } else {
        for (Course course : student.getPreferences()) {
          if (course.getEnrolledStudents().size() < course.getMaxEnrollment()) {
            course.setSmallestGpa(student.getGpa());
            course.getEnrolledStudents().add((MasterStudent) student);
            student.setAssignedCourse(course);
            break;
          } else {
            if (student.getGpa() == course.getSmallestGpa()) {
              course.getEnrolledStudents().add((MasterStudent) student);
              student.setAssignedCourse(course);
              break;
            }
          }
        }
      }
    }

    sortCourses();

    for (Student student : students) {
      if (student.getAssignedCourse() == null) {
        for (Course course : courses) {
          if (course.getEnrolledStudents().size() < course.getMaxEnrollment()) {
            course.getEnrolledStudents().add(student);
            student.setAssignedCourse(course);
            break;
          }
        }
      }
    }

  }

  /**
   * Post course. It writes the course and the students enrolled in it in the output file.
   *
   * @param courseName  the course name
   * @param outputFile  the output file
   * @throws IOException the io exception
   */
  void postCourse(String courseName, String outputFile) throws IOException {
    FileWriter fw = new FileWriter(outputFile, true);
    BufferedWriter bw = new BufferedWriter(fw);

    for (Course<?> course : courses) {
      if (course.getCourseName().equals(courseName)) {
        course.sortEnrolledStudents();
        try {
          bw.write("***");
          bw.newLine();
          bw.write(courseName + " (" + course.getMaxEnrollment() + ")");
          bw.newLine();
        } catch (IOException ignored) {

        }
          for (Student student : course.getEnrolledStudents()) {
            try {
              bw.write(student.getName() + " - " + student.getGpa());
              bw.newLine();
            } catch (IOException ignored) {

            }
          }


        break;
      }
    }

    bw.close();
  }

  /**
   * Post student. It writes the students in the output file.
   *
   * @param studentName the student name
   * @param outputFile  the output file
   * @throws IOException the io exception
   */
  void postStudent(String studentName, String outputFile) throws IOException {
    FileWriter fw = new FileWriter(outputFile, true);
    BufferedWriter bw = new BufferedWriter(fw);

    for (Student student : students) {
      if (student.getName().equals(studentName)) {
        try {
          bw.write("***");
          bw.newLine();
        } catch (IOException ignored) {

        }
        if (student instanceof BachelorStudent) {
          try {
            bw.write("Student Licenta: ");
          } catch (IOException ignored) {

          }
        } else {
          try {
            bw.write("Student Master: ");
          } catch (IOException ignored) {

          }
        }
        try {
          bw.write(studentName + " - " + student.getGpa() + " - " + student.getAssignedCourse().getCourseName());
          bw.newLine();
        } catch (IOException ignored) {

        }
        break;
      }
    }
    bw.close();
  }

  /**
   * Sort courses. It sorts the courses by their names.
   */
  void sortCourses() {
    Collections.sort(courses, Comparator
            .comparing(Course::getCourseName));
  }

}
