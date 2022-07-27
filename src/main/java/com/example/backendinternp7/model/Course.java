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
@Table(name = "courses")
public class Course {

  @Id
  @Column(name = "course_id")
  @GeneratedValue
  private Long courseId;

  @Column(name = "course_name")
  private String courseName;

  @Column(name = "capacity")
  private Integer capacity;

  @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
  @ManyToOne(cascade = {CascadeType.ALL})
  private Teacher teacher;

  @JoinTable(
      name = "student_course",
      joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
  )
  @ManyToMany(cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Student> students;
}
