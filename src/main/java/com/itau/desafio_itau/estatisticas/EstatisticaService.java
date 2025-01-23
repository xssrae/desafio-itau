package com.itau.desafio_itau.estatisticas;

import com.itau.desafio_itau.transacoes.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstatisticaService {

    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        limparTransacoesAntigas();
    }


    public Estatistica calcularEstatisticas() {
        limparTransacoesAntigas();

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

    private void limparTransacoesAntigas() {
        OffsetDateTime limiteInferior = OffsetDateTime.now().minusSeconds(60);
        transacoes.removeIf(transacao ->
                transacao.getDataHora().isBefore(limiteInferior));
    }

}
