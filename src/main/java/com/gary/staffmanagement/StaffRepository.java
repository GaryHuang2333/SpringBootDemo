package com.gary.staffmanagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    List<Staff> findByName(String name);
    List<Staff> findByDepartment(String department);
    List<Staff> findByAgeAndDepartment(Integer age, String department);
    List<Staff> findByAgeBetween(Integer age1, Integer age2);
}
