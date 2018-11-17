package com.gary.staffmanagement.controller;

import com.gary.staffmanagement.domain.Staff;
import com.gary.staffmanagement.repository.StaffRepository;
import com.gary.staffmanagement.domain.Result;
import com.gary.staffmanagement.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffService staffService;

    //获取全员工类表
    @GetMapping("/staffs")
    public Result getAll(){
        return staffService.findAll();
    }

    //根据id获取一个员工 http://localhost:8082/sm/staffs/id?id=70
    @GetMapping("/staffs/id")
    public Result getById(@RequestParam("id") Integer id){
        return staffService.findById(id);
    }

    //根据名字获取员工列表
    @GetMapping("/staffs/name")
    public Result getByName(@RequestParam("name") String name){
        return staffService.findByName(name);
    }

    //根据年龄和部门获取员工类表
    @GetMapping("/staffs/age_department")
    public Result getByAgeNDepartment(@RequestParam("age") Integer age, @RequestParam("department") String  department){
        return staffService.findByAgeAndDepartment(age,department);
    }

    //新建一个员工
    @PostMapping("/staffs")
    public Result createStaff(@Valid Staff staff, BindingResult bindingResult){
        return staffService.saveStaff(staff,bindingResult);
    }



    //新建多个员工
    @PostMapping("/staffs/multiple")
    public List<Staff> createStaffs(){
        List<Staff> staffs = new ArrayList<Staff>();
        Staff s1 = new Staff("001","jerry",26,"male","dev");
        Staff s2 = new Staff("002","tom",30,"male","dev");
        Staff s3 = new Staff("003","rose",23,"female","sale");
        Staff s4 = new Staff("004","jack",28,"male","sale");
        Staff s5 = new Staff("005","tonny",28,"male","hr");
        Staff s6 = new Staff("006","jimmy",38,"male","hr");
        Staff s7 = new Staff("007","john",28,"male","dev");
        Staff s8 = new Staff("008","sisi",26,"female","dev");
        Staff s9 = new Staff("009","lily",28,"female","sale");
        Staff s10 = new Staff("010","neal",35,"male","sale");
        staffs.add(s1);
        staffs.add(s2);
        staffs.add(s3);
        staffs.add(s4);
        staffs.add(s5);
        staffs.add(s6);
        staffs.add(s7);
        staffs.add(s8);
        staffs.add(s9);
        staffs.add(s10);
        return staffRepository.saveAll(staffs);
    }

    //根据id更新一个员工
    @PutMapping("/staffs/id")
    public Staff updateStaff(@RequestParam("id") Integer id){
        Staff staff = new Staff("0004","ben",40,"male","dev");
        staff.setId(id);
        return staffRepository.save(staff);

    }

    @PutMapping("/staffs/ageById")
    public  Result updateStaffAgeById(@RequestParam("id") Integer id, @RequestParam("age") Integer age) throws Exception{
        return staffService.saveAgeById(id,age);
    }

    //根据部门更新多个员工
    @PutMapping("/staffs/department")
    public List<Staff> updateStaffsByDepartment(@RequestParam("department") String department){
        List<Staff> staffs = staffRepository.findByDepartment(department);
        if(staffs != null && staffs.size()>0){
            for(Staff s : staffs){
                s.setComment("Department_"+department+"_updated");
            }
            return staffRepository.saveAll(staffs);
        }
        return new ArrayList<Staff>();
    }

    //根据年龄范围更新多个员工
    @PutMapping("/staffs/age")
    public List<Staff> updateStaffsByAgeRange(@RequestParam("age1") Integer age1, @RequestParam("age2") Integer age2){
        List<Staff> staffs = staffRepository.findByAgeBetween(age1, age2);

        if(staffs != null && staffs.size()>0){
            for(Staff s : staffs){
                s.setComment("Age_"+s.getAge()+"_updated");
            }
            return staffRepository.saveAll(staffs);
        }
        return new ArrayList<Staff>();
    }

    //根据id删除一个员工
    @DeleteMapping("/staffs/id")
    public String deleteById(@RequestParam("id") Integer id){
        staffRepository.deleteById(id);

        if(staffRepository.findById(id).isPresent()){
            return "delete failed";
        }else{
            return "delete successfully";
        }
    }

    //根据部门删除多个员工
    @DeleteMapping("/staffs/department")
    public String deleteByDepartment(@RequestParam("department") String department){
        List<Staff> staffs = staffRepository.findByDepartment(department);
        staffRepository.deleteInBatch(staffs);

        staffs = staffRepository.findByDepartment(department);

        if(staffs == null || staffs.size() < 1){
            return "delete successfully";
        }else{
            return "delete failed";
        }

    }

    //根据年龄范围删除多个员工
    @DeleteMapping("/staffs/age")
    public String deleteByAgeRange(@RequestParam("age1") Integer age1, @RequestParam("age2") Integer age2){
        List<Staff> staffs = staffRepository.findByAgeBetween(age1, age2);
        staffRepository.deleteInBatch(staffs);

        staffs = staffRepository.findByAgeBetween(age1, age2);

        if(staffs == null || staffs.size() < 1){
            return "delete successfully";
        }else{
            return "delete failed";
        }
    }

    //删除所有员工
    @DeleteMapping("/staffs")
    public String deleteAll(){
        staffRepository.deleteAll();

        List<Staff> staffs = staffRepository.findAll();

        if(staffs == null || staffs.size() < 1){
            return "delete successfully";
        }else{
            return "delete failed";
        }
    }

    //test transaction false
    @PostMapping("/staffs/inserttwo")
    public void insertTwo(){
        staffService.testTransactionFalse();
    }

}
