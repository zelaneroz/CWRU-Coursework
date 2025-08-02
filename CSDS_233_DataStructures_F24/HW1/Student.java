package HW1;

import java.util.ArrayList;
public class Student {
    private String name;
    private String studentId;
    private ArrayList<Integer> grades = new ArrayList<Integer>();

    public Student(String name1, String studentId1){
        name = name1;
        studentId=studentId1;
    }

    public void addGrade(int grade){
        grades.add(grade);
    }

    public float getAverageGrade(){
        if (grades.size() == 0) {
            System.out.println("HW1.Student has no existing record. Please add grades.");// Handle case where there are no grades
            return 0.0f;
        }

        float avg = 0.0f;
        for(int i=0;i<grades.size();i++){
            avg+=grades.get(i);
        }
        return avg/grades.size();
    }
}

// Create a test class to demonstrate creating a HW1.Student object, adding some grades, and calculating the average grade.