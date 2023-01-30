package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;
    static Student s = null;
    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));

        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

    }
    
    @Test
    void TestgetStudentByEmail() {
//    	assertj test
    	
    	s = new Student("bolaji@gmail.com", "bolaji saibu", "password");

    	assertThat(s.getEmail())
    	.isEqualTo(studentService.getStudentByEmail("bolaji@gmail.com").getEmail());
    	
    	//JUnit test
//    	s = new Student();
//    	s.setEmail("ariadna@gmail.com");
//    	s.setName("ariadna ramirez");
//    	s.setPassword("passwor");
//    	Student actual = studentService.getStudentByEmail("ariadna@gmail.com");
//    	assertEquals(s.getEmail(), actual.getEmail());
    
    } 
    
}