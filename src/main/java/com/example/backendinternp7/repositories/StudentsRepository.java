package com.example.backendinternp7.repositories;

import com.example.backendinternp7.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentsRepository extends CrudRepository<Student, String> {

  @Query("select s from Student s where s.averageScore > ?1")
  List<Student> getStudentsByAverageScore(double avgScore);

}
