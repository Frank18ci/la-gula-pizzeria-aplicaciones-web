package com.cibertec.producer;

import com.cibertec.configuration.RabbitConfig;
import com.cibertec.rabbit.UserResponseRabbit;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendUser(UserResponseRabbit user){
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_USER, user);
    }
}
