package com.gary.staffmanagement.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Staff {

    @Id
    @GeneratedValue
    private Integer id;

    private String staffNo;
    private String name;

    @Min(value = 18, message = "minimum age is 18 !")
    @Max(value = 60, message = "maximum age is 18")
    private Integer age;
    private String gender;
    private String department;
    private String comment;

    public Staff() {
    }

    public Staff(String staffNo, String name, Integer age, String gender, String department) {
        this.staffNo = staffNo;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", staffNo='" + staffNo + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
