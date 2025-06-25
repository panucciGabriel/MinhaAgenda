package com.gabrielpanucci.agenda.meuprojeto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroDeValidacaoDTO {
    private String campo;
    private String mensagem;
}
