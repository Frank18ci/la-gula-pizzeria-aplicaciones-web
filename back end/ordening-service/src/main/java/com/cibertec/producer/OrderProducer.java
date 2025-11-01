package com.cibertec.producer;

import com.cibertec.configuration.RabbitConfig;
import com.cibertec.rabbit.OrderResponseRabbit;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendOrder(OrderResponseRabbit order) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDER, order);
    }
}
