package com.sitiomarcio.sitiomarcio.controller;

import com.sitiomarcio.sitiomarcio.dto.ReservaRequestDTO;
import com.sitiomarcio.sitiomarcio.dto.ReservaResponseDTO;
import com.sitiomarcio.sitiomarcio.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/cadastrar")
    public ReservaResponseDTO cadastrar(@RequestBody ReservaRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return reservaService.cadastrarReserva(dto, username);
    }

    @GetMapping("/listar")
    public List<ReservaResponseDTO> listar() {
        return reservaService.listarReservas();
    }
    

    @PutMapping("/atualizar/{id}")
    public ReservaResponseDTO atualizar(@PathVariable Long id, @RequestBody ReservaRequestDTO dto) {
        return reservaService.atualizarReserva(id, dto);
    }

    @DeleteMapping("deletar/{id}")
    public void deletar (@PathVariable Long id) {
        reservaService.deletarReserva(id);
    }
}
