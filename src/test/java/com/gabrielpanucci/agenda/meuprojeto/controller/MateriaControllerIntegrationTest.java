// Local: src/test/java/.../controller/MateriaControllerIntegrationTest.java
package com.gabrielpanucci.agenda.meuprojeto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielpanucci.agenda.meuprojeto.Materia;
import com.gabrielpanucci.agenda.meuprojeto.NivelPrioridade;
import com.gabrielpanucci.agenda.meuprojeto.repository.MateriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 1. Carrega o contexto completo da aplicação Spring para o teste
@AutoConfigureMockMvc // 2. Configura um objeto para simular requisições HTTP sem precisar de um servidor real
@Transactional // 3. IMPORTANTE: Faz com que cada teste rode em uma transação que é desfeita (rollback) no final
class MateriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // 4. Nosso "Postman dentro do código" para fazer as requisições

    @Autowired
    private ObjectMapper objectMapper; // 5. Para converter nossos objetos Java para JSON

    @Autowired
    private MateriaRepository materiaRepository; // 6. Acesso direto ao repositório para verificações

    @Test
    @DisplayName("Deve cadastrar uma nova materia e retornar status 200 OK")
    void deveCadastrarNovaMateriaComSucesso() throws Exception {
        // Arrange (Arrumar o cenário)
        Materia novaMateria = new Materia(null, "Física Quântica", NivelPrioridade.ALTA, new ArrayList<>());
        String novaMateriaJson = objectMapper.writeValueAsString(novaMateria); // Converte o objeto para uma String JSON

        // Act & Assert (Agir e Afirmar)
        mockMvc.perform(post("/api/materias") // Simula uma requisição POST para nossa URL
                        .contentType(MediaType.APPLICATION_JSON) // Diz que estamos enviando JSON
                        .content(novaMateriaJson) // O corpo da requisição é a nossa String JSON
                        .with(httpBasic("user", "12345"))) // 7. Simula a autenticação HTTP Basic
                .andExpect(status().isOk()) // 8. AFIRMA que o status HTTP da resposta é 200 OK
                .andExpect(jsonPath("$.id").exists()) // 9. AFIRMA que a resposta JSON tem um campo "id"
                .andExpect(jsonPath("$.nome").value("Física Quântica")); // 10. AFIRMA que o campo "nome" tem o valor correto

        // Assert (Verificação final no banco de dados)
        List<Materia> materiasNoBanco = materiaRepository.findAll();
        assertThat(materiasNoBanco).hasSize(1);
        assertThat(materiasNoBanco.get(0).getNome()).isEqualTo("Física Quântica");
    }

    // Local: src/test/java/.../controller/MateriaControllerIntegrationTest.java
// ... (dentro da classe MateriaControllerIntegrationTest)

    @Test
    @DisplayName("Deve listar todas as materias existentes")
    void deveListarTodasAsMaterias() throws Exception {
        // Arrange: Salva uma matéria no banco de dados de teste para garantir que a lista não esteja vazia.
        materiaRepository.save(new Materia(null, "Física Clássica", NivelPrioridade.MEDIA, new ArrayList<>()));

        // Act & Assert
        mockMvc.perform(get("/api/materias") // Simula uma requisição GET
                        .with(httpBasic("user", "12345"))) // Autentica como usuário
                .andExpect(status().isOk()) // Espera um status 200 OK
                .andExpect(jsonPath("$").isArray()) // Espera que a resposta seja um array JSON
                .andExpect(jsonPath("$[0].nome").value("Física Clássica")); // Espera que o nome do primeiro item seja o correto
    }

    @Test
    @DisplayName("Deve atualizar uma materia existente com sucesso")
    void deveAtualizarUmaMateria() throws Exception {
        // Arrange: Primeiro, cria e salva uma matéria para termos o que atualizar.
        Materia materiaOriginal = materiaRepository.save(new Materia(null, "Nome Antigo", NivelPrioridade.BAIXA, new ArrayList<>()));

        // Cria um objeto com os dados atualizados.
        Materia materiaAtualizada = new Materia(materiaOriginal.getId(), "Nome Novo", NivelPrioridade.ALTA, new ArrayList<>());
        String materiaAtualizadaJson = objectMapper.writeValueAsString(materiaAtualizada);

        // Act & Assert
        mockMvc.perform(put("/api/materias/" + materiaOriginal.getId()) // Faz um PUT para a URL com o ID da matéria criada
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaAtualizadaJson)
                        .with(httpBasic("user", "12345")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Novo"))
                .andExpect(jsonPath("$.prioridade").value("ALTA"));
    }

    @Test
    @DisplayName("Deve deletar uma materia com sucesso quando o usuário é ADMIN")
    void deveDeletarUmaMateriaComSucesso() throws Exception {
        // Arrange: Salva uma matéria para termos o que deletar.
        Materia materiaParaDeletar = materiaRepository.save(new Materia(null, "Matéria a ser Deletada", NivelPrioridade.BAIXA, new ArrayList<>()));

        // Act & Assert
        mockMvc.perform(delete("/api/materias/" + materiaParaDeletar.getId()) // Faz um DELETE para a URL com o ID
                        .with(httpBasic("admin", "admin123"))) // Autentica como ADMIN
                .andExpect(status().isNoContent()); // Espera um status 204 No Content, que indica sucesso na exclusão

        // Assert (Verificação final no banco)
        // Garante que a matéria foi realmente removida do banco de dados.
        assertThat(materiaRepository.findById(materiaParaDeletar.getId())).isEmpty();
    }

}