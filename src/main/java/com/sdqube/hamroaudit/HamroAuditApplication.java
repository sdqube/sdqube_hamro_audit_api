package com.sdqube.hamroaudit;

import com.sdqube.hamroaudit.messaging.UserEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@EnableBinding(UserEventStream.class)
public class HamroAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(HamroAuditApplication.class, args);
    }

}
