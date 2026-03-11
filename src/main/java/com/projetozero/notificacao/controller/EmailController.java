package com.projetozero.notificacao.controller;


import com.projetozero.notificacao.business.EmailService;
import com.projetozero.notificacao.business.dto.TarefasDTORecord;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {


    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefasDTORecord dto) {
           emailService.enviarEmail(dto);
           return ResponseEntity.ok().build();
    }
}
