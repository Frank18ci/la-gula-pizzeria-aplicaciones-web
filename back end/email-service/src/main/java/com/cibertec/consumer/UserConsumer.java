package com.cibertec.consumer;

import com.cibertec.configuration.RabbitConfig;
import com.cibertec.email.template.EmailTemplates;
import com.cibertec.rabbit.UserResponseRabbit;
import com.cibertec.service.MailManagerPersonalizado;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final MailManagerPersonalizado mailManager;

    @RabbitListener(queues = RabbitConfig.QUEUE_USER)
    public void receiveUser(UserResponseRabbit userResponseRabbit) {
        String body = String.format(
                EmailTemplates.HTML_TEMPLATE_USER,
                userResponseRabbit.getRoleName(),
                userResponseRabbit.getFullName(),
                userResponseRabbit.getEmail(),
                userResponseRabbit.getPhone(),
                userResponseRabbit.getStatus(),
                userResponseRabbit.getCreatedAt() != null ? userResponseRabbit.getCreatedAt().toLocalDate() : LocalDateTime.now(),
                Year.now()
        );
        mailManager.sendMessage(
                userResponseRabbit.getEmail(),
                "Bienvenido a La Gula Pizzeria",
                body
        );

    }
}
