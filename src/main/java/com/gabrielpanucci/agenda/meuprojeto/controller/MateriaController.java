package com.gabrielpanucci.agenda.meuprojeto.controller;

import com.gabrielpanucci.agenda.meuprojeto.Materia;
import com.gabrielpanucci.agenda.meuprojeto.repository.MateriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    @GetMapping
    public List<Materia> listarTodasAsMaterias() {
        return materiaRepository.findAll();
    }

    @PostMapping
    public Materia cadastrarMateria(@Valid @RequestBody Materia novaMateria) {
        // Garante que os tópicos estão associados à matéria antes de salvar
        novaMateria.getTopicos().forEach(topico -> topico.setMateria(novaMateria));
        return materiaRepository.save(novaMateria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> atualizarMateria(@PathVariable Long id, @Valid @RequestBody Materia materiaAtualizada) {
        return materiaRepository.findById(id)
                .map(materiaExistente -> {
                    materiaExistente.setNome(materiaAtualizada.getNome());
                    materiaExistente.setPrioridade(materiaAtualizada.getPrioridade());
                    materiaExistente.getTopicos().clear();
                    materiaExistente.getTopicos().addAll(materiaAtualizada.getTopicos());
                    materiaExistente.getTopicos().forEach(topico -> topico.setMateria(materiaExistente));
                    Materia materiaSalva = materiaRepository.save(materiaExistente);
                    return ResponseEntity.ok(materiaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMateria(@PathVariable Long id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}