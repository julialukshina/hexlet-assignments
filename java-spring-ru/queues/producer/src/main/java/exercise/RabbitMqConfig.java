package exercise;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // BEGIN
    @Bean
    Queue queue() {
       //задаем имя очереди
        return new Queue("queue", false);
    }

    @Bean
    TopicExchange exchange() {
        //задаем имя обменника
        return new TopicExchange("exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        // Сообщения с ключом "exchange.key" будут направлены в очередь "queue"
        return BindingBuilder.bind(queue).to(exchange).with("exchange.key");
    }
    // END
}
