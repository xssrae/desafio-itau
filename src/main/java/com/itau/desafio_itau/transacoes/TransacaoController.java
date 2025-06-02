package com.itau.desafio_itau.transacoes;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransacaoController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);
    private final TransacoesService transacoesService;
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacoesService transacoesService, TransacaoRepository transacaoRepository) {
        this.transacoesService = transacoesService;
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping
    public ResponseEntity<String> limpar() {
        log.info("Limpando Transacoes");
        transacaoRepository.limpar();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Todas as transações foram removidas com sucesso");
    }

    @GetMapping
    public ResponseEntity<TransacaoResponse> list() {
        try {
            List<Transacao> transacoes = transacoesService.list();
            if (transacoes.isEmpty()) {
                return ResponseEntity
                        .ok(new TransacaoResponse("Nenhuma transação realizada"));
            }
            return ResponseEntity.ok(new TransacaoResponse(transacoes));
        } catch (Exception e) {
            log.error("Erro ao listar transações", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransacaoResponse("Erro ao buscar transações: " + e.getMessage()));
        }
    }

    private void validarTransacao(Transacao transacao) throws ValidationException {
        if (transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O valor da transação deve ser maior que zero");
        }

        if (transacao.getDataHora() != null && transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new ValidationException("A data da transação não pode ser futura");
        }
    }
}
