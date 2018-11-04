package com.gary.staffmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
