package com.example.backendinternp7.controllers;

import com.example.backendinternp7.model.Teacher;
import com.example.backendinternp7.repositories.TeachersRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RestController
public class TeacherController {

  private TeachersRepository teacherService;

  @PostMapping("/teacher")
  public ResponseEntity addTeacher(@RequestBody TeacherRequest teacher) {
    teacherService.save(new Teacher(teacher.getTeacherId(), teacher.getFullName()));
    return ResponseEntity.status(201).body(teacher);
  }

  @Getter
  @NoArgsConstructor
  class TeacherRequest {
    @NotBlank
    private String teacherId;
    @NotBlank
    private String fullName;
  }
}
