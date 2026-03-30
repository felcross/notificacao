package com.projetozero.notificacao.business.service;

import com.projetozero.notificacao.business.dto.NotificacaoEmailRecord;
import com.projetozero.notificacao.infrastructure.exception.EmailException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setup() {
        // Injeta o valor do remetente manualmente já que não temos o application.properties no teste unitário
        ReflectionTestUtils.setField(emailService, "remetente", "projeto-zero@teste.com");
    }

    @Test
    @DisplayName("Deve enviar e-mail com sucesso quando os dados forem válidos")
    void enviarEmailSucesso() {
        // GIVEN
        NotificacaoEmailRecord dto = new NotificacaoEmailRecord(
                "cliente@teste.com", "Assunto", "Usuario", "Mensagem", null, "template-venda"
        );

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html>Conteudo</html>");

        // WHEN
        assertDoesNotThrow(() -> emailService.enviarEmailGenerico(dto));

        // THEN
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("Deve lançar EmailException quando o JavaMailSender falhar")
    void deveLancarEmailException() {
        // GIVEN
        NotificacaoEmailRecord dto = new NotificacaoEmailRecord(
                "erro@teste.com", "Assunto", "User", "Msg", null, "template"
        );

        // Simula que o MailSender explode ao tentar criar a mensagem (causando a MessagingException interna)
        when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException("Erro de Conexão SMTP"));

        // WHEN & THEN
        // Aqui validamos se o seu Service encapsulou o erro na sua EmailException customizada
        assertThrows(EmailException.class, () -> emailService.enviarEmailGenerico(dto));

        // Garante que o send NUNCA foi chamado já que deu erro antes
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }
}