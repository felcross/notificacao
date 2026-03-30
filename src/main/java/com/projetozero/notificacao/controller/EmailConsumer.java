package com.projetozero.notificacao.controller;

import com.projetozero.notificacao.business.service.EmailService;
import com.projetozero.notificacao.business.dto.NotificacaoEmailRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "q.enviar-email")
    public void escutarFilaEmail(@Payload NotificacaoEmailRecord dto) {
        System.out.println("Mensagem recebida para o e-mail: " + dto.destinatario());

        // Aqui o Consumer apenas repassa para o Service processar o SMTP
        emailService.enviarEmailGenerico(dto);
    }
}