package com.example.backendinternp7.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

  @Id
  @Column(name = "student_id")
  private String studentId;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "on_probation")
  private Boolean onProbation;

  @Column(name = "average_score")
  private Double averageScore;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "favorite_course", referencedColumnName = "course_id")
  private Course favoriteCourse;

  @JoinTable(
      name = "student_course",
      joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
  )
  @ManyToMany(cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Course> studentCourses;

}
