package com.itau.desafio_itau.transacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacoesService {

    @Autowired
    private final TransacaoRepository transacaoRepository;

    public TransacoesService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<Transacao> create(Transacao transacao) {
        transacaoRepository.add(transacao);
        return list();
    }

    public List<Transacao> list() {
        OffsetDateTime limite = OffsetDateTime.now().minusMinutes(5);

        // Filtra transações que ocorreram nos últimos 5 minutos
        return transacaoRepository.list().stream()
                .filter(transacao -> transacao.getDataHora() != null && !transacao.getDataHora().isBefore(limite))
                .collect(Collectors.toList());
    }

    // Remove transações que ocorreram nos ultimos 5 minutos
    public void removerTransacoesAntigas() {
        OffsetDateTime limite = OffsetDateTime.now().minusMinutes(5);
        transacaoRepository.removerAntigas(limite);
    }
}