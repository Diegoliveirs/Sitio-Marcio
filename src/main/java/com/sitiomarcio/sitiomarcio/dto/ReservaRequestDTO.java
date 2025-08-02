package com.sitiomarcio.sitiomarcio.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    private String nomeCliente;
    private String telefone;
    private LocalDate dtEntrada;
    private LocalDate dtSaida;
    private BigDecimal diaria;
    private String observacao;
    private Long codusu;
}
