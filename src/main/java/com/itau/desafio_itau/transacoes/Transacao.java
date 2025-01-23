package com.itau.desafio_itau.transacoes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class Transacao {
    private Long id;
    private BigDecimal valor;
    private OffsetDateTime dataHora;
    private String tipo;
    private String conta;

    public Transacao(Long id, BigDecimal valor, String tipo, OffsetDateTime dataHora, String conta) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConta() {
        return conta;
    }
    public void setConta(String conta) {
        this.conta = conta;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public OffsetDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
