package com.sitiomarcio.sitiomarcio.controller;

import com.sitiomarcio.sitiomarcio.dto.LoginDTO;
import com.sitiomarcio.sitiomarcio.dto.UsuarioDTO;
import com.sitiomarcio.sitiomarcio.dto.UsuarioRequestDTO;
import com.sitiomarcio.sitiomarcio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public UsuarioDTO cadastrar(@RequestBody UsuarioRequestDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @PostMapping("/login")
    public UsuarioDTO login(@RequestBody LoginDTO dto) {
        return usuarioService.login(dto);
    }
}
