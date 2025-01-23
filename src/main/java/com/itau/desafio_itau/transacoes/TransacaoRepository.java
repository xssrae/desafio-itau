package com.itau.desafio_itau.transacoes;

import org.springframework.stereotype.Repository;

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

    public List<Transacao> findAll() {
        return new ArrayList<>(transacoes);  // retorna uma cópia da lista
    }

    public void deleteById(Long id) {
        transacoes.removeIf(t -> t.getId().equals(id));
    }

    public void limpar() {
        transacoes.clear();
        nextId = 1L;
    }
}