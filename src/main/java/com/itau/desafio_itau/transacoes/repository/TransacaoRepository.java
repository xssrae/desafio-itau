package com.itau.desafio_itau.transacoes.repository;

import com.itau.desafio_itau.transacoes.entity.Transacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransacaoRepository {
    private final List<Transacao> transacoes = new ArrayList<>();

    public void add(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> findAll() {
        return new ArrayList<>(transacoes); // Retorna uma cópia da lista
    }

    public void deleteById(Long id) {
        transacoes.removeIf(transacao -> transacao.getId().equals(id));
    }

    public void limpar() {
        transacoes.clear();
    }

    public Optional<Transacao> findById(Long id) {
        return transacoes.stream()
                .filter(transacao -> transacao.getId().equals(id))
                .findFirst();
    }
}
