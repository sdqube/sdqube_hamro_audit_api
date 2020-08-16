package com.sdqube.hamroaudit.repository;

import com.sdqube.hamroaudit.model.UserBill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 2:06 PM
 */
public interface UserBillRepository extends MongoRepository<UserBill, String> {

    UserBill findByBillId(String billId);
    List<UserBill> findByUserId(String userId);
    List<UserBill> findByUserIdAndAndBillType(String userId, String billType);
    List<UserBill> findByUsername(String username);
    List<UserBill> findByCreatedAtBetween(Instant createdAt, Instant createdAt2);
    Boolean existsByUserId(String userId);
    Boolean existsByUsername(String username);
}
