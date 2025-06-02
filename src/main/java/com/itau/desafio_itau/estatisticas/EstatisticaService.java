package com.itau.desafio_itau.estatisticas;

import com.itau.desafio_itau.transacoes.Transacao;
import com.itau.desafio_itau.transacoes.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class EstatisticaService {
    private final TransacoesService transacoesService;

    @Autowired
    public EstatisticaService(TransacoesService transacoesService) {
        this.transacoesService = transacoesService;
    }

    public Estatistica calcularEstatisticas() {
        List<Transacao> transacoes = transacoesService.list();

        if (transacoes.isEmpty()) {
            return new Estatistica(0L, BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO);
        }

        long count = transacoes.size();

        BigDecimal sum = transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

        BigDecimal min = transacoes.stream()
                .map(Transacao::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = transacoes.stream()
                .map(Transacao::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return new Estatistica(count, sum, avg, min, max);
    }
}