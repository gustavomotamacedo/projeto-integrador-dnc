package com.dnc.gustavomota.projeto_integrador_dnc.controllers;

import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.CpfDuplicadoException;
import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.RecursoNaoEncontradoException;
import com.dnc.gustavomota.projeto_integrador_dnc.models.Aluno;
import com.dnc.gustavomota.projeto_integrador_dnc.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Aluno> buscarPorCpf(@PathVariable String cpf) {
        try {
            Aluno aluno = alunoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable String matricula) {
        try {
            Aluno aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        try {
            Aluno alunoSalvo = alunoService.salvar(aluno);
            return ResponseEntity.ok(alunoSalvo); // Retorna 200 OK com o aluno criado
        } catch (CpfDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409 Conflict
        } catch (Exception e) {
            // Pode ser refinado para capturar outras exceções específicas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
            return ResponseEntity.ok(alunoAtualizado); // Retorna 200 OK com o aluno atualizado
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o aluno não existir
        } catch (CpfDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409 Conflict se CPF duplicado
        } catch (Exception e) {
            // Pode ser refinado para capturar outras exceções específicas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = alunoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } else {
            // O serviço pode lançar RecursoNaoEncontradoException se o ID não for encontrado
            // e o GlobalExceptionHandler cuidará disso.
            // Alternativamente, o serviço pode retornar boolean e o controller lidar assim:
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o aluno não existir
        }
    }
}
