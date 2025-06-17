package com.example.demo.service;

import com.example.demo.model.OutstandingFee;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FeeService {

    public List<OutstandingFee> getOutstandingFees() {
        // Real data from Software Engineering database
        OutstandingFee fee1 = new OutstandingFee();
        fee1.setStudentID(22221001);
        fee1.setName("Alfred Darkwa");
        fee1.setOutstandingAmount(500); // 1000 owed - 500 paid

        OutstandingFee fee2 = new OutstandingFee();
        fee2.setStudentID(22221002);
        fee2.setName("Daniel Fugar");
        fee2.setOutstandingAmount(900); // 1200 owed - 300 paid

        OutstandingFee fee3 = new OutstandingFee();
        fee3.setStudentID(22222221);
        fee3.setName("Nana Yaw Asiedu");
        fee3.setOutstandingAmount(0); // 800 owed - 800 paid (fully paid)

        OutstandingFee fee4 = new OutstandingFee();
        fee4.setStudentID(22222222);
        fee4.setName("John Ahiatrogah");
        fee4.setOutstandingAmount(-500); // 500 owed - 1000 paid (overpaid)

        return Arrays.asList(fee1, fee2, fee3, fee4);
    }
}