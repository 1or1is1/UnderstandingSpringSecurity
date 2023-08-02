package com.example.SpringSecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> students = Arrays.asList(
            new Student(1, "James"),
            new Student(2, "Maria"),
            new Student(3, "Anna")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        System.out.println("============== GET ALL STUDENTS ==============");
        return students;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public String registerNewStudent(@RequestBody Student student) {
        System.out.println("============== REGISTER STUDENT START ==============");
        System.out.println(student);
        System.out.println("============== REGISTER STUDENT END ================");
        return "STUDENT REGISTERED SUCCESSFULLY";
    }

    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public String deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("============== DELETING STUDENT START ==============");
        System.out.println("STUDENT ID : "+studentId);
        System.out.println("============== DELETING STUDENT END ================");
        return "STUDENT DELETED SUCCESSFULLY";
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('student:write')")
    public String updateStudent(@RequestBody Student student) {
        System.out.println("============== UPDATE STUDENT START ==============");
        System.out.println("UPDATING STUDENT : "+student);
        System.out.println("============== UPDATE STUDENT END ================");
        return "STUDENT UPDATED SUCCESSFULLY";
    }

}
