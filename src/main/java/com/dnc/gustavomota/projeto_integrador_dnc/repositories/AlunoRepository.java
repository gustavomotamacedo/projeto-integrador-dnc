package com.dnc.gustavomota.projeto_integrador_dnc.repositories;

import com.dnc.gustavomota.projeto_integrador_dnc.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByMatricula(String matricula);
}
