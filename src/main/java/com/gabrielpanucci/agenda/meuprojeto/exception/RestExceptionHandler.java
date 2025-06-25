// Local: src/main/java/.../exception/RestExceptionHandler.java
package com.gabrielpanucci.agenda.meuprojeto.exception;

import com.gabrielpanucci.agenda.meuprojeto.dto.ErroDeValidacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice // 1. Anotação que torna esta classe um "vigia" global para os controllers
public class RestExceptionHandler {

    // 2. Este método será chamado quando uma validação (@Valid) falhar
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 3. Garante que o status da resposta seja 400
    @ExceptionHandler(MethodArgumentNotValidException.class) // 4. Especifica a exceção a ser tratada
    public List<ErroDeValidacaoDTO> handle(MethodArgumentNotValidException exception) {

        List<ErroDeValidacaoDTO> dto = new ArrayList<>();

        // 5. Pega todos os erros de campo da exceção
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        // 6. Para cada erro de campo, cria nosso DTO customizado e o adiciona à lista
        fieldErrors.forEach(e -> {
            ErroDeValidacaoDTO erro = new ErroDeValidacaoDTO(e.getField(), e.getDefaultMessage());
            dto.add(erro);
        });

        return dto;
    }
}