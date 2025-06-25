package com.gabrielpanucci.agenda.meuprojeto.service;

import com.gabrielpanucci.agenda.meuprojeto.Topico;
import com.gabrielpanucci.agenda.meuprojeto.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Topico registrarEstudo(Long topicoId) {
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com o ID: " + topicoId));
        topico.setDataUltimoEstudo(LocalDateTime.now());
        return topicoRepository.save(topico);
    }
}