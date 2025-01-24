package com.itau.desafio_itau.estatisticas;

import com.itau.desafio_itau.transacoes.Transacao;
import com.itau.desafio_itau.transacoes.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;
    private Transacao transacoes;

    @GetMapping
    public ResponseEntity<Estatistica> getEstatisticas() {
        return ResponseEntity.ok(estatisticaService.calcularEstatisticas());
    }

}
