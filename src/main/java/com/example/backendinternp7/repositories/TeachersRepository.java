package com.example.backendinternp7.repositories;

import com.example.backendinternp7.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeachersRepository extends CrudRepository<Teacher, String> {
}
