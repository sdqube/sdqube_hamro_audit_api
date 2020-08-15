package com.sdqube.hamroaudit.messaging;

import com.sdqube.hamroaudit.endpoint.AuthController;
import com.sdqube.hamroaudit.model.User;
import com.sdqube.hamroaudit.payload.UserEventPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 4:38 PM
 */
@Service
public class UserActivityEventHandler {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserEventStream channels;

    public UserActivityEventHandler(UserEventStream channels) {
        this.channels = channels;
    }

    public void sendUserCreated(User user) {
        log.info("Sending user created event for user {}", user.getUsername());
        sendUserChangedEvent(generateEventPayload(user, UserEventType.CREATED));
    }

    public void sendUserUpdated(User user) {
        log.info("sending user updated event for user {}", user.getUsername());
        sendUserChangedEvent(generateEventPayload(user, UserEventType.UPDATED));
    }

    public void sendUserUpdated(User user, String oldPicUrl) {
        log.info("sending user updated (profile pic changed) event for user {}",
                user.getUsername());

        UserEventPayload payload = generateEventPayload(user, UserEventType.UPDATED);
        payload.setOldProfilePicUrl(oldPicUrl);

        sendUserChangedEvent(payload);
    }

    private void sendUserChangedEvent(UserEventPayload payload) {
        Message<UserEventPayload> message = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.MESSAGE_KEY, payload.getId())
                .build();
        channels.momentsUserChanged().send(message);
        log.info("User event {} send to topic {} for user {}",
                message.getPayload().getEventType().name(),
                channels.OUTPUT,
                message.getPayload().getUsername());

    }

    public UserEventPayload generateEventPayload(User user, UserEventType userEventType) {
        UserEventPayload userEventPayload = new UserEventPayload();
        userEventPayload.setEventType(userEventType);
        userEventPayload.setId(user.getId());
        userEventPayload.setUsername(user.getUsername());
        userEventPayload.setEmail(user.getEmail());
        userEventPayload.setDisplayName(user.getUserProfile().getDisplayName());
        userEventPayload.setProfilePictureUrl(user.getUserProfile().getProfilePictureUrl());

        return userEventPayload;
    }
}
