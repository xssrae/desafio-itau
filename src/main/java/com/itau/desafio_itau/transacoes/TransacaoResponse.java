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

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
