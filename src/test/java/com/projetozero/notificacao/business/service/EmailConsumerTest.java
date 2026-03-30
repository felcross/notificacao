package com.projetozero.notificacao.business.service;

import com.projetozero.notificacao.business.dto.NotificacaoEmailRecord;
import com.projetozero.notificacao.controller.EmailConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailConsumerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Test
    @DisplayName("Deve receber mensagem da fila e repassar para o EmailService")
    void deveEscutarFilaERepassar() {
        // GIVEN
        NotificacaoEmailRecord dto = new NotificacaoEmailRecord(
                "dest@e.com", "Assunto", "Nome", "Msg", null, "temp"
        );

        // WHEN
        emailConsumer.escutarFilaEmail(dto);

        // THEN
        verify(emailService, times(1)).enviarEmailGenerico(dto);
    }
}
