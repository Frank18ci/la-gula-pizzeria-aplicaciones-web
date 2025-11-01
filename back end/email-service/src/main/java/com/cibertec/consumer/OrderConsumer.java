package com.cibertec.consumer;

import com.cibertec.configuration.RabbitConfig;
import com.cibertec.rabbit.OrderItemResponseRabbit;
import com.cibertec.rabbit.OrderResponseRabbit;
import com.cibertec.service.MailManagerPersonalizado;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

import static com.cibertec.email.template.EmailTemplates.HTML_TEMPLATE_ORDER;

@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final MailManagerPersonalizado mailManager;

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER)
    public void receiveOrder(OrderResponseRabbit orderRequest) {
        System.out.println("Received Order Message: " + orderRequest);
        mailManager.sendMessage(
                orderRequest.getCustomerEmail(),
                "Gracias por su orden #" + orderRequest.getOrderId(),
                buildOrderEmailHtml(orderRequest)
        );
    }

    public String buildOrderEmailHtml(OrderResponseRabbit order) {
        String itemsHtml = buildOrderItemsHtml(order.getOrderItems());

        return String.format(
                HTML_TEMPLATE_ORDER,
                order.getOrderId(),
                order.getCustomerName(),
                order.getOrderStatus(),
                order.getCustomerEmail(),
                order.getAmount(),
                itemsHtml,
                Year.now()
        );
    }

    private String buildOrderItemsHtml(List<OrderItemResponseRabbit> items) {
        StringBuilder sb = new StringBuilder();
        for (OrderItemResponseRabbit item : items) {
            sb.append("<table class='items'>")
                    .append("<tr><th>Pizza</th><th>Tamaño</th><th>Masa</th><th>Cantidad</th></tr>")
                    .append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%d</td></tr>",
                            item.getPizzaName(), item.getSizeName(), item.getDoughTypeName(), item.getQuantity()));

            if (item.getOrderItemToppings() != null && !item.getOrderItemToppings().isEmpty()) {
                sb.append("<tr><td colspan='4'><div class='topping'>Toppings: ");
                List<String> toppingList = item.getOrderItemToppings()
                        .stream()
                        .map(t -> t.getToppingName() + " (x" + t.getQuantity() + ")")
                        .toList();
                sb.append(String.join(", ", toppingList)).append("</div></td></tr>");
            }
            sb.append("</table><br>");
        }
        return sb.toString();
    }
}
