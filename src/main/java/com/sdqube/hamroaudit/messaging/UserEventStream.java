package com.sdqube.hamroaudit.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/3/20 1:28 AM
 */
public interface UserEventStream {

    String OUTPUT = "momentsUserChanged";

    @Output(OUTPUT)
    MessageChannel momentsUserChanged();
}
