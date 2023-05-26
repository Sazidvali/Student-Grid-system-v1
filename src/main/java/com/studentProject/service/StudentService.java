package com.studentProject.service;


import com.studentProject.entity.Student;
import com.studentProject.filter.FilterCriteria;
import com.studentProject.payload.StudentDTO;
import com.studentProject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository; // If using a database

    // If using a file-based approach, you can add file reading logic here
    public StudentDTO createNewPost(StudentDTO studentDTO){
       Student student=mapToEntity(studentDTO);
       Student s1 =studentRepository.save(student);
      StudentDTO studentDTO1= mapToDTO(s1);
      return studentDTO1;

    }
    private StudentDTO mapToDTO(Student student){
        StudentDTO d = new StudentDTO();
        d.setId(student.getId());
        d.setName(student.getName());
        d.setTotalMarks(student.getTotalMarks());
        return d;
    }
    private Student mapToEntity(StudentDTO studentDTO){
        Student s = new Student();
        s.setId(studentDTO.getId());
        s.setName(studentDTO.getName());
        s.setTotalMarks(studentDTO.getTotalMarks());
        return s;
    }
    public List<Student> getStudents(int page, int pageSize) {
        // Calculate the starting index and ending index for pagination
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Retrieve all students from the data source
        List<Student> allStudents = studentRepository.findAll(); // If using a database
        // If using a file-based approach, you can implement the reading logic here

        // Apply pagination
        List<Student> paginatedStudents = new ArrayList<>();
        for (int i = startIndex; i < endIndex && i < allStudents.size(); i++) {
            paginatedStudents.add(allStudents.get(i));
        }

        return paginatedStudents;
    }

    public List<Student> filterStudents(FilterCriteria filterCriteria) {
        List<Student> allStudents = studentRepository.findAll(); // If using a database
        // If using a file-based approach, you can implement the reading logic here

        // Apply filtering based on the filter criteria
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : allStudents) {
            if (matchesFilterCriteria(student, filterCriteria)) {
                filteredStudents.add(student);
            }
        }

        return filteredStudents;
    }

    private boolean matchesFilterCriteria(Student student, FilterCriteria filterCriteria) {
        if (filterCriteria.getName() != null && !filterCriteria.getName().isEmpty()) {
            // Apply filtering based on the name field
            if (!student.getName().contains(filterCriteria.getName())) {
                return false;
            }
        }

        // Add other filter criteria checks as needed

        return true;
    }
}
