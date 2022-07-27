package com.example.backendinternp7.controllers;

import com.example.backendinternp7.model.Course;
import com.example.backendinternp7.model.Teacher;
import com.example.backendinternp7.repositories.CoursesRepository;
import com.example.backendinternp7.repositories.StudentsRepository;
import com.example.backendinternp7.repositories.TeachersRepository;
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
public class CourseController {

  private CoursesRepository coursesRepository;
  private TeachersRepository teachersRepository;
  private StudentsRepository studentsRepository;

  @PostMapping("/course")
  public ResponseEntity addCourse(@Valid @RequestBody CourseRequest course) {
    Course courseResult = new Course(null, course.getCourseName(), course.getCapacity(), null, null);
    if (course.getTeacherId() != null) {
      var teacherResult = teachersRepository.findById(course.getTeacherId());
      if (teacherResult.isEmpty()) {
        return ResponseEntity.status(404).body("teacherId Not found!");
      } else {
        courseResult.setTeacher(teacherResult.get());
      }
    }
    coursesRepository.save(courseResult);
    return ResponseEntity.status(201).body(courseResult);
  }

  @PostMapping("/course/{course_id}/add_student/{student_id}")
  public ResponseEntity addStudentToCourse(@PathVariable("course_id") Long courseId, @PathVariable("student_id") String studentId) {
    var course = coursesRepository.findById(courseId);
    if (course.isEmpty()) {
      return ResponseEntity.status(404).body("courseId Not Found");
    }
    var student = studentsRepository.findById(studentId);
    if (student.isEmpty()) {
      return ResponseEntity.status(404).body("Student Not Found");
    }
    course.get().getStudents().add(student.get());
    coursesRepository.save(course.get());
    return ResponseEntity.status(201).build();
  }

  @DeleteMapping("/course/{course_id}/add_student/{student_id}")
  public ResponseEntity deleteStudentFromCourse(@PathVariable("course_id") Long courseId, @PathVariable("student_id") String studentId) {
    var course = coursesRepository.findById(courseId);
    if (course.isEmpty()) {
      return ResponseEntity.status(404).body("courseId Not Found");
    }
    for (int i = 0; i < course.get().getStudents().size(); i++) {
      var student = course.get().getStudents().get(i);
      if (student.getStudentId().equals(studentId)) {
        course.get().getStudents().remove(i);
        coursesRepository.save(course.get());
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.status(404).body("studentId Not Found");
  }

  @NoArgsConstructor
  @Getter
  class CourseRequest {
    @NotBlank
    private String courseName;
    @NotNull
    private Integer capacity;
    @NotBlank
    private String teacherId;
  }
}