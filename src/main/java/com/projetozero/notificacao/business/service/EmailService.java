package com.projetozero.notificacao.business.service;



import com.projetozero.notificacao.business.dto.NotificacaoEmailRecord;
import com.projetozero.notificacao.infrastructure.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente:feljava32@gmail.com}")
    private String remetente;

    public void enviarEmailGenerico(NotificacaoEmailRecord dto) {
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(dto.destinatario());
            helper.setSubject(dto.assunto());

            Context context = new Context();
            // Adiciona todas as variáveis que vieram no Map do DTO
            if (dto.variaveisTemplate() != null) {
                dto.variaveisTemplate().forEach((chave, valor) -> context.setVariable(chave, valor));
            }
            context.setVariable("nomeUsuario", dto.nomeUsuario());
            context.setVariable("mensagem", dto.mensagemCorpo());

            // O templateName vem do DTO (ex: "vendas", "tarefas")
            String content = templateEngine.process(dto.templateName(), context);

            helper.setText(content, true);
            javaMailSender.send(mensagem);
        } catch (MessagingException | RuntimeException e) {
            throw new EmailException("Erro ao processar e-mail", e);
        }
    }
}
