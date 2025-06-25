package com.gabrielpanucci.agenda.meuprojeto.controller;

import com.gabrielpanucci.agenda.meuprojeto.Topico;
import com.gabrielpanucci.agenda.meuprojeto.service.EstudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudo")
public class EstudoController {

    @Autowired
    private EstudoService estudoService;

    @GetMapping("/sugestao")
    public ResponseEntity<Topico> obterSugestao() {
        return estudoService.sugerirProximoTopico()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}