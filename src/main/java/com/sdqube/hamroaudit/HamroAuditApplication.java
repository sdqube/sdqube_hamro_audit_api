package com.sdqube.hamroaudit;

import com.sdqube.hamroaudit.messaging.UserEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableMongoAuditing
@EnableBinding(UserEventStream.class)
//@EntityScan(basePackages = "com.sdqube.hamroaudit")
public class HamroAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(HamroAuditApplication.class, args);
    }
}
