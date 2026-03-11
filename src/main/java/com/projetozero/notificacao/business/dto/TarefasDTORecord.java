package com.projetozero.notificacao.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projetozero.notificacao.business.enuns.StatusTarefaEnum;


import java.time.LocalDateTime;

public record TarefasDTORecord(
        String id,
        String nomeTarefa,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataCriacao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataAgendamento,
        String emailUsuario,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataAlteracao,
        StatusTarefaEnum status
) {}
