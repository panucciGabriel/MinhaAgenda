package com.gabrielpanucci.agenda.meuprojeto.repository;

import com.gabrielpanucci.agenda.meuprojeto.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}