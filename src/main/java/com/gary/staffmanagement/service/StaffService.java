package com.gary.staffmanagement.service;

import com.gary.staffmanagement.constants.StaffConstant;
import com.gary.staffmanagement.constants.StaffEnum;
import com.gary.staffmanagement.domain.Staff;
import com.gary.staffmanagement.exceptions.StaffException;
import com.gary.staffmanagement.repository.StaffRepository;
import com.gary.staffmanagement.domain.Result;
import com.gary.staffmanagement.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Transactional
    public void testTransactionFalse(){
        Staff s1 = new Staff("77777777","name",30,"female","test department");
        Staff s2 = new Staff("999999999","name",30,"female","test department");

        staffRepository.save(s1);
        staffRepository.save(s2);
    }

    public Result saveStaff(Staff staff, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.fail(bindingResult.getFieldError().getDefaultMessage());
        }else{
            staffRepository.save(staff);
            return ResultUtil.success(staff);
        }
    }

    public Result findById(Integer id){
        Optional<Staff> opt = staffRepository.findById(id);
        return returnResult(opt);
    }

    public Result findAll(){
        List<Staff> list  = staffRepository.findAll();
        return returnResult(list);
    }

    public Result findByName(String name){
        List<Staff> list = staffRepository.findByName(name);
        return returnResult(list);
    }


    public Result findByAgeAndDepartment(Integer age, String department){
        List<Staff> list = staffRepository.findByAgeAndDepartment(age,department);
        return  returnResult(list);

    }

    public Result saveAgeById(Integer id, Integer age) throws Exception{
        Result result;
        Optional<Staff> opt = staffRepository.findById(id);
        Result tmpResult = returnResult(opt);

        if(age == StaffEnum.ERROR.getCode()){
            throw new StaffException(StaffEnum.ERROR);
        }else if(age == StaffEnum.ERROR_UNKNOWN.getCode()){
            throw new StaffException(StaffEnum.ERROR_UNKNOWN);
        }else if(age == StaffEnum.ERROR_MIN_AGE.getCode()){
            throw new StaffException(StaffEnum.ERROR_MIN_AGE);
        }else if(age == StaffEnum.ERROR_MAX_AGE.getCode()) {
            throw new StaffException(StaffEnum.ERROR_MAX_AGE);
        }else if(age == StaffEnum.ERROR_NOT_FOUND.getCode()){
            throw new StaffException(StaffEnum.ERROR_NOT_FOUND);
        }else if(age == StaffEnum.SUCCESS.getCode()){
            throw new StaffException(StaffEnum.SUCCESS);
        }else if(age == StaffEnum.ERROR_SYSTEM.getCode()){
            throw new Exception(StaffEnum.ERROR_SYSTEM.getMessage());
        }

        if(StaffConstant.RESULT_NOT_FOUND.equals(tmpResult.getMessage())){
            result = tmpResult;
        }else if(StaffConstant.RESULT_SUCCESS.equals(tmpResult.getMessage())){
            Staff staff = opt.orElse(new Staff());
            if(age > 60){
//                result = ResultUtil.fail(StaffConstant.RESULT_MESSAGE_MAX_AGE);
                throw new StaffException(StaffEnum.ERROR_MAX_AGE);
            }else if(age < 18){
//                result = ResultUtil.fail(StaffConstant.RESULT_MESSAGE_MIN_AGE);
                throw new StaffException(StaffEnum.ERROR_MIN_AGE);
            }else{
                staff.setAge(age);
                staffRepository.save(staff);
                result = ResultUtil.success(staff);
            }
        }else{
//            result = ResultUtil.fail(StaffConstant.RESULT_UNKNOWN);
            throw new StaffException(StaffEnum.ERROR_UNKNOWN);
        }

        return result;
    }

    private Result returnResult(List<Staff> list){
        if(list != null && list.size() >0){
            return ResultUtil.success(list);
        }else{
            return ResultUtil.fail(StaffConstant.RESULT_NOT_FOUND);
        }
    }

    private Result returnResult(Optional<Staff> opt){
        if(opt.isPresent()) {
            return ResultUtil.success(opt.orElse(new Staff()));

        }else{
            return ResultUtil.fail(StaffConstant.RESULT_NOT_FOUND);
        }
    }
}
