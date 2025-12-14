package com.dnc.gustavomota.projeto_integrador_dnc.controllers;

import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.CpfDuplicadoException;
import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.RecursoNaoEncontradoException;
import com.dnc.gustavomota.projeto_integrador_dnc.models.Professor;
import com.dnc.gustavomota.projeto_integrador_dnc.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos() {
        List<Professor> professores = professorService.listarTodos();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        try {
            Professor professor = professorService.buscarPorId(id);
            return ResponseEntity.ok(professor);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Professor> buscarPorCpf(@PathVariable String cpf) {
        try {
            Professor professor = professorService.buscarPorCpf(cpf);
            return ResponseEntity.ok(professor);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Professor>> buscarPorDepartamento(@PathVariable String departamento) {
        try {
            List<Professor> professores = professorService.buscarPorDepartamento(departamento);
            return ResponseEntity.ok(professores);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        try {
            Professor professorSalvo = professorService.salvar(professor);
            return ResponseEntity.ok(professorSalvo); // Retorna 200 OK com o professor criado
        } catch (CpfDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409 Conflict
        } catch (Exception e) {
            // Pode ser refinado para capturar outras exceções específicas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professor) {
        try {
            Professor professorAtualizado = professorService.atualizar(id, professor);
            return ResponseEntity.ok(professorAtualizado); // Retorna 200 OK com o professor atualizado
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o professor não existir
        } catch (CpfDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Retorna 409 Conflict se CPF duplicado
        } catch (Exception e) {
            // Pode ser refinado para capturar outras exceções específicas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = professorService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o professor não existir
        }
    }
}
