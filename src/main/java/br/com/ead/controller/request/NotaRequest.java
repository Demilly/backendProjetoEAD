package br.com.ead.controller.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotaRequest {
    private BigDecimal valor;
    private Long idModulo;
}