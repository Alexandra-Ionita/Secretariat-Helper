#### Name: Ioniță Alexandra-Mihaela
#### Personal Email: alexandraionita901@gmail.com

# Secretariat Application

### Homework description:
In this homework I have created a program that allocates each student to a 
facultative based on their preferences. The program reads the data from a file
and writes the result in another file. 

### Implementation details:
For implementing this homework I have used 6 classes.

### Classes used:
- Main: the class that contains the main method and the method that reads the
  data from the file and searches for the command in the list of commands
- Secretariat: the class that contains the list of students and the list of
  facultatives and the methods for adding students and facultatives to the
  lists and for allocating the students to facultatives
- Student: the class that contains the name, the preferences and the allocated
  facultative of a student and the methods for getting and setting the name,
  the preferences and the allocated facultative
- MasterStudent: the class that extends the Student class and contains the
  methods for getting and setting the name, the preferences and the allocated
  facultative for a master's student
- BachelorStudent: the class that extends the Student class and contains the
  methods for getting and setting the name, the preferences and the allocated
  facultative for a bachelor's student
- Course: the class that contains the name, the number of places and the list
  of students of a facultative and the methods for getting and setting the
  name, the number of places and the list of students


###  Collections and interfaces used:
- ArrayList: used for storing the list of students and the list of facultatives.
I used this collection because it is easy to add and remove elements from it.
- Collections.sort: used for sorting the list of students and the list of
facultatives. I used this method because it is easy to sort the elements of a
list using it.
- Comparator: used for comparing the students based on their grade and name. I
used this interface because it is easy to compare the elements of a list using
it.
- Generic types: used for Course class to store the list of students. I used
this because it is easy to store the elements of a list using it, 
especially because the list can contain both master's and bachelor's students.
- Exception

### Bonus:
- I have implemented a bonus part of the project. I have allocated the 
remained students that could not be allocated to the preferred facultative.
I have sorted the students based on their grade from highest to lowest and
sorted the courses alphabetically. The first student from the list is allocated
to the first course from the list that have free places, the second student 
from the list is allocated to the second course from the list and so on.

