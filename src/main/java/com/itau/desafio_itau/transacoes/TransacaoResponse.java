package com.itau.desafio_itau.transacoes;

import java.util.Collections;
import java.util.List;

public class TransacaoResponse {
    private List<Transacao> transacoes;
    private String mensagem;

    public TransacaoResponse(String mensagem) {
        this.mensagem = mensagem;
        this.transacoes = Collections.emptyList();
    }

    public TransacaoResponse(List<Transacao> transacoes) {
        this.transacoes = transacoes != null ? transacoes : Collections.emptyList();
        this.mensagem = null;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public String getMensagem() {
        return mensagem;
    }
}