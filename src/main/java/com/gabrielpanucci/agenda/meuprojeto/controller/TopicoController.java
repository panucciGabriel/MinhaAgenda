package com.gabrielpanucci.agenda.meuprojeto.controller;

import com.gabrielpanucci.agenda.meuprojeto.Topico;
import com.gabrielpanucci.agenda.meuprojeto.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping("/{id}/registrar-estudo")
    public ResponseEntity<Topico> registrarEstudo(@PathVariable Long id) {
        try {
            Topico topicoAtualizado = topicoService.registrarEstudo(id);
            return ResponseEntity.ok(topicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}