package com.sitiomarcio.sitiomarcio.repository;

import com.sitiomarcio.sitiomarcio.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByDtEntradaLessThanEqualAndDtSaidaGreaterThanEqual(LocalDate dtSaida, LocalDate dtEntrada);
}
