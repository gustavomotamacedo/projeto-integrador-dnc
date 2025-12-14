package com.dnc.gustavomota.projeto_integrador_dnc.exceptions;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String mensagem) {
        super(mensagem);
    }
}