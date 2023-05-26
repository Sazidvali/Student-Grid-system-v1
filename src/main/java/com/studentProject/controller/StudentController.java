package com.studentProject.controller;


import com.studentProject.entity.Student;
import com.studentProject.filter.FilterCriteria;
import com.studentProject.payload.StudentDTO;
import com.studentProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    //http://localhost:8080/students/posting
    @PostMapping("/posting")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO){
       return new ResponseEntity<> (studentService.createNewPost(studentDTO), HttpStatus.CREATED);
    }
    //http://localhost:8080/students/get
    @GetMapping("/get")
    public List<StudentDTO> getAllStudents(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        List<Student> students = studentService.getStudents(page, pageSize);
        return convertToDTO(students);
    }
    //http://localhost:8080/students/filter
    @PostMapping("/filter")
    public List<StudentDTO> filterStudents(@RequestBody FilterCriteria filterCriteria) {
        List<Student> filteredStudents = studentService.filterStudents(filterCriteria);
        return convertToDTO(filteredStudents);
    }

    private List<StudentDTO> convertToDTO(List<Student> students) {
        // Convert Student entities to StudentDTOs
        // Implement the conversion logic here
        // You can use libraries like ModelMapper or manually map the fields

        // Example conversion using manual mapping
        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getName(), student.getTotalMarks());
            studentDTOs.add(studentDTO);
        }

        return studentDTOs;
    }
}
