package org.example;

import java.io.*;

public class Main {
  /**
   * Executes a command.
   * @param commandParts the command to be executed and its arguments
   * @param secretariat the secretariat
   * @param testToBeExecuted the test to be executed
   * @param outputFile the output file
   * @throws IOException if an I/O error occurs
   */
  private static void executeCommand(String[] commandParts,
                                     Secretariat secretariat,
                                     String testToBeExecuted, String outputFile) throws IOException {
    switch(commandParts[0]) {
      case "adauga_student":
        try {
          secretariat.addStudent(commandParts[1], commandParts[2]);
        } catch (IllegalArgumentException e) {
          e.getMessage();
          try {
            FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("***");
            bw.newLine();
            bw.write(e.getMessage());
            bw.newLine();
            bw.close();
          } catch (IOException ioException) {
            ioException.fillInStackTrace();
          }
        }
        break;

      case "citeste_mediile":
        secretariat.readGrades(testToBeExecuted);
        break;

      case "posteaza_mediile":
        secretariat.writeGrades(outputFile);
        break;

      case "contestatie":
        secretariat.gradeAppeal(commandParts[1], commandParts[2]);
        break;

      case "adauga_curs":
        secretariat.addCourse(commandParts[1], commandParts[2], commandParts[3]);
        break;

      case "adauga_preferinte":
        secretariat.addPreferences(commandParts);
        break;

      case "repartizeaza":
        secretariat.studentsAllocation();
        break;

      case "posteaza_curs":
        secretariat.postCourse(commandParts[1], outputFile);
        break;

      case "posteaza_student":
        secretariat.postStudent(commandParts[1], outputFile);
        break;

    }
  }

  /**
   * Reads the commands from the input file.
   * @param commandFile the input file
   * @param secretariat the secretariat
   * @param testToBeExecuted the test to be executed
   * @param outputFile the output file
   * @throws IOException if an I/O error occurs
   */
  private static void commandsReader(String commandFile,
                                     Secretariat secretariat,
                                     String testToBeExecuted,
                                     String outputFile) throws IOException {
    BufferedReader br = null;

    try {
      FileReader fr = new FileReader(commandFile);
      br = new BufferedReader(fr);

      while (true) {
        String command = br.readLine();
        if (command == null) {
          break;
        }

        String[] commandParts = command.split(" - ");

        executeCommand(commandParts, secretariat, testToBeExecuted, outputFile);
      }

    } catch (Exception e) {
      e.fillInStackTrace();
    } finally {
      assert br != null;
      br.close();
    }
  }

  /**
   * The main method.
   * @param args the command line arguments
   * @throws IOException if an I/O error occurs
   */
  public static void main(String[] args) throws IOException {
    String testToBeExecuted = "src/main/resources/" + args[0];
    String commandFile = testToBeExecuted + "/" + args[0] + ".in";
    String outputFile = testToBeExecuted + "/" + args[0] + ".out";

    Secretariat secretariat = new Secretariat();

    commandsReader(commandFile, secretariat, testToBeExecuted, outputFile);
  }
}
