package com.itau.desafio_itau.transacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream()
                .sorted(Comparator.comparing(Transacao::getId).reversed()
                        .thenComparing(Transacao::getValor))
                .collect(Collectors.toList());
    }

    public List<Transacao> delete(Long id) {
        transacaoRepository.deleteById(id);
        return list();
    }

}