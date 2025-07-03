// Local: src/test/java/.../service/EstudoServiceTest.java
package com.gabrielpanucci.agenda.meuprojeto.service;

import com.gabrielpanucci.agenda.meuprojeto.Materia;
import com.gabrielpanucci.agenda.meuprojeto.NivelPrioridade;
import com.gabrielpanucci.agenda.meuprojeto.Topico;
import com.gabrielpanucci.agenda.meuprojeto.repository.TopicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. Habilita a integração com o Mockito
class EstudoServiceTest {

    @Mock // 2. Cria um "dublê" (mock) do nosso repositório
    private TopicoRepository topicoRepository;

    @InjectMocks // 3. Cria uma instância do nosso serviço e injeta os mocks (@Mock) nele
    private EstudoService estudoService;

    @Test // 4. Marca este método como um caso de teste executável
    @DisplayName("Deve sugerir o tópico de maior prioridade que nunca foi estudado")
    void deveSugerirTopicoDeMaiorPrioridadeNuncaEstudado() {
        // Arrange (Arrumar o cenário)
        Materia materiaAlta = new Materia(1L, "Java Avançado", NivelPrioridade.ALTA, null);
        Materia materiaBaixa = new Materia(2L, "História", NivelPrioridade.BAIXA, null);

        // Tópico de prioridade baixa, mas estudado há muito tempo
        Topico topicoAntigo = new Topico(1L, "Revolução Francesa", "", "", LocalDateTime.now().minusDays(10), materiaBaixa);

        // Tópico de prioridade alta, nunca estudado (data nula) -> DEVE SER O ESCOLHIDO
        Topico topicoUrgente = new Topico(2L, "Spring Security", "", "", null, materiaAlta);

        // Configura o nosso repositório falso para retornar estes tópicos quando for chamado
        when(topicoRepository.findAll()).thenReturn(List.of(topicoAntigo, topicoUrgente));

        // Act (Agir)
        Optional<Topico> sugestao = estudoService.sugerirProximoTopico();

        // Assert (Afirmar o resultado)
        assertThat(sugestao).isPresent(); // Afirma que uma sugestão foi retornada
        assertThat(sugestao.get().getNome()).isEqualTo("Spring Security"); // Afirma que a sugestão é a correta
    }

    // Local: src/test/java/.../service/EstudoServiceTest.java
    // ... (dentro da classe EstudoServiceTest)

    @Test
    @DisplayName("Deve sugerir o tópico estudado há mais tempo quando todos já foram estudados")
    void deveSugerirTopicoEstudadoHaMaisTempo() {
        // Arrange (Arrumar o cenário)
        Materia materia = new Materia(1L, "Geral", NivelPrioridade.MEDIA, null);

        // Tópico estudado há 2 dias.
        Topico topicoRecente = new Topico(1L, "Tópico Recente", "", "", LocalDateTime.now().minusDays(2), materia);

        // Tópico estudado há 10 dias. -> DEVE SER O ESCOLHIDO
        Topico topicoAntigo = new Topico(2L, "Tópico Antigo", "", "", LocalDateTime.now().minusDays(10), materia);

        when(topicoRepository.findAll()).thenReturn(List.of(topicoRecente, topicoAntigo));

        // Act (Agir)
        Optional<Topico> sugestao = estudoService.sugerirProximoTopico();

        // Assert (Afirmar o resultado)
        assertThat(sugestao).isPresent();
        assertThat(sugestao.get().getNome()).isEqualTo("Tópico Antigo");
    }

}