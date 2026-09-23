package HW1;

public class StudentTest {

    public static void main(String[] args) {
        // Test 1: Create a student and check the initial average grade (should be 0.0)
        Student student = new Student("Alice", "S1234");
        System.out.println("Test 1 - Initial Average Grade: " + student.getAverageGrade());  // Expected: 0.0

        // Test 2: Add a single grade and check average
        student.addGrade(85);
        System.out.println("Test 2 - After Adding 85: " + student.getAverageGrade());  // Expected: 85.0

        // Test 3: Add multiple grades and check average
        student.addGrade(90);
        student.addGrade(75);
        student.addGrade(95);
        System.out.println("Test 3 - After Adding More Grades: " + student.getAverageGrade());  // Expected: (85+90+75+95)/4 = 86.25

        // Test 4: Adding a grade of 100 and checking average
        student.addGrade(100);
        System.out.println("Test 4 - After Adding 100: " + student.getAverageGrade());  // Expected: (85+90+75+95+100)/5 = 89.0

        //Test 5: Create a new student. Get average grade without any records. //Expected: ERROR MESSAGE
        Student student2 = new Student("Zee", "S1362");
        System.out.println("Test 5 - Average Grade of New HW1.Student with no existing record - "+ student2.getAverageGrade());

    }
}
