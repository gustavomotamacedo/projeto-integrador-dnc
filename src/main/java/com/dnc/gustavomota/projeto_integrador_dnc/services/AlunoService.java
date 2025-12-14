package com.dnc.gustavomota.projeto_integrador_dnc.services;

import com.dnc.gustavomota.projeto_integrador_dnc.dto.ViaCepResponse;
import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.CpfDuplicadoException;
import com.dnc.gustavomota.projeto_integrador_dnc.exceptions.RecursoNaoEncontradoException;
import com.dnc.gustavomota.projeto_integrador_dnc.models.Aluno;
import com.dnc.gustavomota.projeto_integrador_dnc.models.Endereco;
import com.dnc.gustavomota.projeto_integrador_dnc.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ViaCepService viaCepService;

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos;
    }

    public Aluno buscarPorId(Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            return alunoOpt.get();
        } else {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
    }

    public Aluno buscarPorCpf(String cpf) {
        Optional<Aluno> alunoOpt = alunoRepository.findByCpf(cpf);
        if (alunoOpt.isPresent()) {
            return alunoOpt.get();
        } else {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com CPF: " + cpf);
        }
    }

    public Aluno buscarPorMatricula(String matricula) {
        Optional<Aluno> alunoOpt = alunoRepository.findByMatricula(matricula);
        if (alunoOpt.isPresent()) {
            return alunoOpt.get();
        } else {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com matrícula: " + matricula);
        }
    }

    public Aluno salvar(Aluno aluno) {

        // Validação: CPF único
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());
        if (alunoExistente.isPresent() && !alunoExistente.get().getId().equals(aluno.getId())) {
            throw new CpfDuplicadoException("CPF já cadastrado: " + aluno.getCpf());
        }

        // Se o CEP estiver presente no endereço, busca os dados atualizados
        Endereco endereco = aluno.getEndereco();
        if (endereco != null && endereco.getCep() != null && !endereco.getCep().isEmpty()) {
            try {
                ViaCepResponse dadosCep = viaCepService.buscarEnderecoPorCep(endereco.getCep());

                // Atualiza os campos do endereço com os dados da API
                endereco.setLogradouro(dadosCep.getLogradouro());
                endereco.setBairro(dadosCep.getBairro());
                endereco.setCidade(dadosCep.getCidade());
                endereco.setEstado(dadosCep.getEstado());

            } catch (Exception e) {
                throw new RuntimeException("Falha ao integrar com a API de CEP.", e);
            }
        }

        Aluno alunoSalvo = alunoRepository.save(aluno);
        return alunoSalvo;
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado) {

        Optional<Aluno> alunoExistenteOpt = alunoRepository.findById(id);
        if (alunoExistenteOpt.isPresent()) {
            Aluno alunoExistente = alunoExistenteOpt.get();

            // Garante que o ID do aluno atualizado seja o mesmo do encontrado
            alunoAtualizado.setId(id);

            // Validação: CPF único (exceto para o próprio aluno sendo atualizado)
            Optional<Aluno> alunoComMesmoCpf = alunoRepository.findByCpf(alunoAtualizado.getCpf());
            if (alunoComMesmoCpf.isPresent() && !alunoComMesmoCpf.get().getId().equals(id)) {
                throw new CpfDuplicadoException("CPF já cadastrado por outro aluno: " + alunoAtualizado.getCpf());
            }

            // Atualizar endereço via ViaCEP (mesma lógica do salvar, se necessário)
            Endereco endereco = alunoAtualizado.getEndereco();
            if (endereco != null && endereco.getCep() != null && !endereco.getCep().isEmpty()) {
                try {
                    ViaCepResponse dadosCep = viaCepService.buscarEnderecoPorCep(endereco.getCep());

                    endereco.setLogradouro(dadosCep.getLogradouro());
                    endereco.setBairro(dadosCep.getBairro());
                    endereco.setCidade(dadosCep.getCidade());
                    endereco.setEstado(dadosCep.getEstado());

                } catch (Exception e) {
                    throw new RuntimeException("Falha ao integrar com a API de CEP durante atualização.", e);
                }
            }

            Aluno alunoAtualizadoSalvo = alunoRepository.save(alunoAtualizado);
            return alunoAtualizadoSalvo;
        } else {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
    }

    public boolean deletar(Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            alunoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}