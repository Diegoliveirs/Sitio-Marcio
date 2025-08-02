package com.sitiomarcio.sitiomarcio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codreserva")
    private Long codReserva;

    @Column(name = "nomecliente", nullable = false)
    private String nomeCliente;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "dtentrada", nullable = false)
    private LocalDate dtEntrada;

    @Column(name = "dtsaida", nullable = false)
    private LocalDate dtSaida;

    @Column(name = "diaria")
    private BigDecimal diaria;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "codusu", nullable = false)
    private Usuario usuario;

}
