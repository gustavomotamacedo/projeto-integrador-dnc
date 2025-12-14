package com.dnc.gustavomota.projeto_integrador_dnc.repositories;

import com.dnc.gustavomota.projeto_integrador_dnc.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByCpf(String cpf);
    List<Professor> findAllByDepartamento(String departamento);
}
