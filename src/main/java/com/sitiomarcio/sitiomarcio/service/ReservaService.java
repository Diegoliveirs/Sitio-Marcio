package com.sitiomarcio.sitiomarcio.service;

import com.sitiomarcio.sitiomarcio.dto.ReservaRequestDTO;
import com.sitiomarcio.sitiomarcio.dto.ReservaResponseDTO;
import com.sitiomarcio.sitiomarcio.model.Reserva;
import com.sitiomarcio.sitiomarcio.model.Usuario;
import com.sitiomarcio.sitiomarcio.repository.ReservaRepository;
import com.sitiomarcio.sitiomarcio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ReservaResponseDTO cadastrarReserva(ReservaRequestDTO dto, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return null;
        }

        if (temConflito(dto.getDtEntrada(), dto.getDtSaida())) {
            throw new RuntimeException("Já existe uma reserva nesse período!");
        }

        Reserva reserva = new Reserva();
        reserva.setNomeCliente(dto.getNomeCliente());
        reserva.setTelefone(dto.getTelefone());
        reserva.setDtEntrada(dto.getDtEntrada());
        reserva.setDtSaida(dto.getDtSaida());
        reserva.setDiaria(dto.getDiaria());
        reserva.setObservacao(dto.getObservacao());
        reserva.setUsuario(usuario);

        Reserva salva = reservaRepository.save(reserva);

        ReservaResponseDTO resposta = new ReservaResponseDTO();
        resposta.setCodReserva(salva.getCodReserva());
        resposta.setNomeCliente(salva.getNomeCliente());
        resposta.setTelefone(salva.getTelefone());
        resposta.setDtEntrada(salva.getDtEntrada());
        resposta.setDtSaida(salva.getDtSaida());
        resposta.setDiaria(salva.getDiaria());
        resposta.setObservacao(salva.getObservacao());
        resposta.setCodusu(usuario.getCodusu());

        return resposta;
    }

    public List<ReservaResponseDTO> listarReservas() {
        return reservaRepository.findAll().stream().map(reserva -> {
            ReservaResponseDTO dto = new ReservaResponseDTO();
            dto.setCodReserva(reserva.getCodReserva());
            dto.setNomeCliente(reserva.getNomeCliente());
            dto.setTelefone(reserva.getTelefone());
            dto.setDtEntrada(reserva.getDtEntrada());
            dto.setDtSaida(reserva.getDtSaida());
            dto.setDiaria(reserva.getDiaria());
            dto.setObservacao(reserva.getObservacao());
            dto.setCodusu(reserva.getUsuario().getCodusu());
            return dto;
        }).collect(Collectors.toList());
    }

    public ReservaResponseDTO atualizarReserva(Long id, ReservaRequestDTO dto, String username) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (reserva == null || usuario == null) {
            return null;
        }

        if (temConflitoAoAtualizar(id, dto.getDtEntrada(), dto.getDtSaida())) {
            throw new RuntimeException("Conflito de data: já existe outra reserva nesse período.");
        }

        reserva.setNomeCliente(dto.getNomeCliente());
        reserva.setTelefone(dto.getTelefone());
        reserva.setDtEntrada(dto.getDtEntrada());
        reserva.setDtSaida(dto.getDtSaida());
        reserva.setDiaria(dto.getDiaria());
        reserva.setObservacao(dto.getObservacao());
        reserva.setUsuario(usuario);

        Reserva atualizada = reservaRepository.save(reserva);

        ReservaResponseDTO resposta = new ReservaResponseDTO();
        resposta.setCodReserva(atualizada.getCodReserva());
        resposta.setNomeCliente(atualizada.getNomeCliente());
        resposta.setTelefone(atualizada.getTelefone());
        resposta.setDtEntrada(atualizada.getDtEntrada());
        resposta.setDtSaida(atualizada.getDtSaida());
        resposta.setDiaria(atualizada.getDiaria());
        resposta.setObservacao(atualizada.getObservacao());
        resposta.setCodusu(usuario.getCodusu());

        return resposta;
    }

    public void deletarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public boolean temConflito(LocalDate entrada, LocalDate saida) {
        return !reservaRepository.findByDtEntradaLessThanEqualAndDtSaidaGreaterThanEqual(entrada, saida).isEmpty();
    }

    public boolean temConflitoAoAtualizar(Long id, LocalDate entrada, LocalDate saida) {
        return reservaRepository
                .findByDtEntradaLessThanEqualAndDtSaidaGreaterThanEqual(entrada, saida)
                .stream()
                .anyMatch(r -> !r.getCodReserva().equals(id));
    }
}
