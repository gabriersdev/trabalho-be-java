package com.task.api.controller;

import com.task.api.dto.ApiResponse;
import com.task.api.model.Tarefa;
import com.task.api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// TODOS os endpoints definidos nesta classe começaram com /tarefas se tiver uma rota específica será /tarefas/[path?]
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Criar uma nova tarefa (método POST)
    @PostMapping
    public ResponseEntity<ApiResponse> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(new ApiResponse(true, "Inserção feita com sucesso", novaTarefa.toString()), HttpStatus.CREATED);
    }

    // Consultar todas as tarefas (método GET)
    @GetMapping
    public ResponseEntity<ApiResponse<List<Tarefa>>> listarTodasAsTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        String mensagem = tarefas.isEmpty() ? "Nenhuma tarefa encontrada." : "Tarefas listadas com sucesso.";
        ApiResponse<List<Tarefa>> resposta = new ApiResponse<>(true, mensagem, tarefas);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    // Consultar uma tarefa por ID (método GET)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(value -> new ResponseEntity<>(new ApiResponse(true, "Tarefa encontrada com sucesso!", value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ApiResponse(true, "Tarefa não encontrada!", ""), HttpStatus.NOT_FOUND));
    }

    // Atualizar uma tarefa (método PUT REST)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);
        if (tarefaExistente.isPresent()) {
            Tarefa tarefaParaAtualizar = tarefaExistente.get();
            tarefaParaAtualizar.setNome(tarefaDetalhes.getNome());
            tarefaParaAtualizar.setDataEntrega(tarefaDetalhes.getDataEntrega());
            tarefaParaAtualizar.setResponsavel(tarefaDetalhes.getResponsavel());
            Tarefa tarefaAtualizada = tarefaRepository.save(tarefaParaAtualizar);
            return new ResponseEntity<>(new ApiResponse(true, "Tarefa atualizada com sucesso!", tarefaAtualizada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Tarefa não foi atualizada!", ""), HttpStatus.NOT_FOUND);
        }
    }

    // Remover uma tarefa (método DELETE REST - Retorna 200 para aparecer resultado no POSTMAN)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removerTarefa(@PathVariable Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(true, "Tarefa deletada com sucesso!", ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "A tarefa não encontrada!", ""), HttpStatus.NOT_FOUND);
        }
    }
}