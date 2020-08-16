package com.sdqube.hamroaudit.service;

import com.sdqube.hamroaudit.model.AuditUserDetails;
import com.sdqube.hamroaudit.model.UserBill;
import com.sdqube.hamroaudit.payload.FileRequest;
import com.sdqube.hamroaudit.payload.FileResponse;
import com.sdqube.hamroaudit.repository.UserBillRepository;
import com.sdqube.hamroaudit.utils.HamroAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 2:11 PM
 */
@Service
public class UserBillService {
    private final Logger log = LoggerFactory.getLogger(UserBillService.class);

    @Autowired
    private S3Services s3Services;

    private final UserBillRepository userBillRepository;

    public UserBillService(UserBillRepository userBillRepository) {
        this.userBillRepository = userBillRepository;
    }

    public UserBill findByBillId(String billId) {
        return userBillRepository.findByBillId(billId);
    }

    public List<UserBill> findByUsername(String username) {
        return userBillRepository.findByUsername(username);
    }

    public List<UserBill> findByUserId(String username) {
        return userBillRepository.findByUserId(username);
    }

    public List<UserBill> getBillsByUserIdType(String userId, String billType) {
        List<UserBill> bills = userBillRepository.findByUserIdAndAndBillType(userId, billType);
        try {
            List<UserBill> newBills = bills.stream().map(bill -> {
                FileResponse newFileResponse = bill.getFileResponse();
                FileResponse s3FileResponse = s3Services.getFile(new FileRequest(newFileResponse.getFilename(), billType));
                newFileResponse.setUrl(s3FileResponse.getUrl());

                bill.setFileResponse(newFileResponse);
                return bill;
            }).collect(Collectors.toList());
            return newBills;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bills;
    }

    public UserBill storeUserBill(String billType, AuditUserDetails userDetails, FileResponse fileResponse) throws Exception {
        UserBill savedUserBill;

        try {
            String billId = HamroAuditUtils.uuid();
            UserBill newUserBill = new UserBill();
            newUserBill.setBillId(billId);
            newUserBill.setUserId(userDetails.getId());
            newUserBill.setUsername(userDetails.getUsername());
            newUserBill.setBillType(billType);
            newUserBill.setFilename(fileResponse.getFilename());
            newUserBill.setFileResponse(fileResponse);

            savedUserBill = userBillRepository.save(newUserBill);
            log.info("User Bill info: {}", savedUserBill);
        } catch (Exception ex) {
            throw new Exception("ERROR: Storing bill to db failed. " + ex);
        }
        return savedUserBill;
    }
}
