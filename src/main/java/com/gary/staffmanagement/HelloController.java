package com.gary.staffmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @Autowired
    private StaffProperties staff;

    @Value("${projectName}")
    private String projectName;

    @RequestMapping(value = {"/hello/{name}","/hi/{name}/hello"}, method = RequestMethod.GET)
    public String hello(@PathVariable("name") String myName){
        return "hello springboot "+myName+"!!! ";
    }

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public String getStaff(){
        return "projectName:"+projectName+"   name="+staff.getName()+"  age="+staff.getAge()+"  gender="+staff.getGender();
    }

    @RequestMapping(value="/say",method = RequestMethod.POST)
    public String say(@RequestParam("name") String myName){
        return "say my name : " + myName;
    }

    @GetMapping("/say")
    public String say2(@RequestParam(value = "name",required = false, defaultValue = "defaultName") String myName,
                       @RequestParam(value = "age",required = false,defaultValue = "10") Integer myAge){
        return "my name : " + myName + "    my age : " + myAge;
    }

    @GetMapping(value = "/bye/{name}")
    public String bye(@PathVariable("name") String myName){
        return "bye my name : " + myName ;
    }
}
