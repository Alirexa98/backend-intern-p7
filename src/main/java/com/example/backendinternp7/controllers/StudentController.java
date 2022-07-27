package com.example.backendinternp7.controllers;

import com.example.backendinternp7.model.Student;
import com.example.backendinternp7.repositories.CoursesRepository;
import com.example.backendinternp7.repositories.StudentsRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RestController
public class StudentController {

  private StudentsRepository studentService;
  private CoursesRepository coursesRepository;

  @PostMapping("/student")
  public ResponseEntity addStudent(@Valid @RequestBody StudentRequest student) {
    if (studentService.findById(student.getStudentId()).isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    return updateStudent(student);
  }

  @PutMapping("/student")
  public ResponseEntity updateStudent(@Valid @RequestBody StudentRequest student) {
    Student studentResult = new Student(student.getStudentId(), student.getFullName(), student.getOnProbation(), student.getAverageScore(), null, null);
    if (student.getFavoriteCourse() != null) {
      var courseResult = coursesRepository.findById(student.getFavoriteCourse());
      if (courseResult.isEmpty()) {
        return ResponseEntity.status(404).body("favoriteCourse Not found!");
      } else {
        studentResult.setFavoriteCourse(courseResult.get());
      }
    }
    studentService.save(studentResult);
    return ResponseEntity.status(201).body(studentResult);
  }

  @GetMapping("/student")
  public ResponseEntity showStudentsWithMinAvgScore(@RequestParam(value = "minAvgScore", defaultValue = "0.0") Double score) {
    return ResponseEntity.ok(studentService.getStudentsByAverageScore(score));
  }

  @Getter
  @NoArgsConstructor
  class StudentRequest {
    @NotBlank
    private String studentId;
    @NotBlank
    private String fullName;
    @NotNull
    private Boolean onProbation;
    private Double averageScore;
    private Long favoriteCourse;
  }
}