package com.itau.desafio_itau.transacoes.controller;

import com.itau.desafio_itau.transacoes.entity.Transacao;
import com.itau.desafio_itau.transacoes.repository.TransacaoRepository;
import com.itau.desafio_itau.transacoes.service.TransacoesService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransacaoController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);
    private TransacoesService transacoesService;

    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping
    public ResponseEntity<String> processarTransacao(@RequestBody Transacao transacao) {
        try {
            validarTransacao(transacao);
            transacaoRepository.add(transacao);
            return ResponseEntity
                    .status(HttpStatus.CREATED).body("Transação enviada com sucesso");

        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Dados da transação inválidos: " + illegalArgumentException.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao processar transação: " + e.getMessage());

        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity limpar() {
        log.info("Limpando Transacoes");
        transacaoRepository.limpar();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Todas as transações foram removidas com sucesso");
    }

    @GetMapping
    List<Transacao> list() {
        return transacoesService.list();
    }

    private void validarTransacao(Transacao transacao) throws ValidationException {
        if (transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O valor da transação deve ser maior que zero");
        }

        if (transacao.getDataHora() != null &&
                transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new ValidationException("A data da transação não pode ser futura");
        }

    }

}
