package com.itau.desafio_itau.transacoes;

import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransacaoRepository {
    private final List<Transacao> transacoes = new ArrayList<>();
    private Long nextId = 1L;

    public void add(Transacao transacao) {
        transacao.setId(nextId++);
        transacoes.add(transacao);
    }

    public List<Transacao> list() {
        return new ArrayList<>(transacoes);
    }

    public void delete() {
        transacoes.clear();
    }

    public void limpar() {
        transacoes.clear();
    }

    public void removerAntigas(OffsetDateTime limite) {
        transacoes.removeIf( transacao -> {
            boolean antiga = transacao.getDataHora() != null && transacao.getDataHora().isBefore(limite);
            if (antiga) {
                System.out.println("Removendo transação antiga: " + transacao.getId() + " - " + transacao.getDataHora());
            }
            return antiga;
        });
    }
}