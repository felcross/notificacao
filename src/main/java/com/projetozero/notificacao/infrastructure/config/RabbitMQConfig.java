package com.projetozero.notificacao.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String QUEUE_EMAIL = "q.enviar-email";


    @Bean
    public Queue queue() {
        return new Queue(QUEUE_EMAIL, false); // durable: true para não perder a fila se o Rabbit cair
    }

    // O "pulo do gato": Transforma o Record em JSON automaticamente na fila
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}