package com.gabrielpanucci.agenda.meuprojeto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do tópico não pode estar em branco.")
    private String nome;

    private String descricao;

    @URL(message = "O link do recurso deve ser uma URL válida.")
    private String linkRecurso;

    // Campo para armazenar a data e hora do último estudo
    private LocalDateTime dataUltimoEstudo;

    // Define a relação: Muitos Tópicos para Uma Matéria.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id") // Coluna no banco que fará a ligação
    @JsonIgnore // Impede loops infinitos ao converter para JSON
    private Materia materia;
}