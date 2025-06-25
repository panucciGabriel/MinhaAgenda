package com.gabrielpanucci.agenda.meuprojeto.repository;

import com.gabrielpanucci.agenda.meuprojeto.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Magia aqui também
}
