package com.gabrielpanucci.agenda.meuprojeto.service;

import com.gabrielpanucci.agenda.meuprojeto.NivelPrioridade;
import com.gabrielpanucci.agenda.meuprojeto.Topico;
import com.gabrielpanucci.agenda.meuprojeto.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EstudoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Optional<Topico> sugerirProximoTopico() {
        List<Topico> todosOsTopicos = topicoRepository.findAll();
        if (todosOsTopicos.isEmpty()) {
            return Optional.empty();
        }
        return todosOsTopicos.stream()
                .max(Comparator.comparing(this::calcularPontuacao));
    }

    private double calcularPontuacao(Topico topico) {
        double pesoPrioridade = getPesoPorPrioridade(topico.getMateria().getPrioridade());
        long horasDesdeEstudo;
        if (topico.getDataUltimoEstudo() == null) {
            horasDesdeEstudo = Long.MAX_VALUE / 10000;
        } else {
            horasDesdeEstudo = Duration.between(topico.getDataUltimoEstudo(), LocalDateTime.now()).toHours();
        }
        return pesoPrioridade * (horasDesdeEstudo + 1);
    }

    private double getPesoPorPrioridade(NivelPrioridade prioridade) {
        switch (prioridade) {
            case ALTA: return 3.0;
            case MEDIA: return 2.0;
            case BAIXA: default: return 1.0;
        }
    }
}