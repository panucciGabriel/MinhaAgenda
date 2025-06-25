package com.gabrielpanucci.agenda.meuprojeto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da matéria não pode estar em branco.")
    private String nome;

    @NotNull(message = "A prioridade não pode ser nula.")
    @Enumerated(EnumType.STRING) // Salva o nome do Enum no banco (ex: "ALTA")
    private NivelPrioridade prioridade;

    // Define a relação: Uma Matéria para Muitos Tópicos.
    // cascade = ALL: Operações na Matéria (salvar, deletar) são aplicadas aos Tópicos.
    // orphanRemoval = true: Se um Tópico for removido desta lista, ele é deletado do banco.
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Topico> topicos = new ArrayList<>();
}