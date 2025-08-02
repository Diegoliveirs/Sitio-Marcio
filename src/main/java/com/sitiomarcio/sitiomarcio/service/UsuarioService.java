package com.sitiomarcio.sitiomarcio.service;

import com.sitiomarcio.sitiomarcio.dto.LoginDTO;
import com.sitiomarcio.sitiomarcio.dto.UsuarioDTO;
import com.sitiomarcio.sitiomarcio.dto.UsuarioRequestDTO;
import com.sitiomarcio.sitiomarcio.model.Usuario;
import com.sitiomarcio.sitiomarcio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO cadastrar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario salvo = usuarioRepository.save(usuario);

        UsuarioDTO resposta = new UsuarioDTO();
        resposta.setCodusu(salvo.getCodusu());
        resposta.setNome(salvo.getNome());
        resposta.setUsername(salvo.getUsername());

        return resposta;
    }

    public UsuarioDTO login(LoginDTO dto) {
        Optional<Usuario> usuarioOpt =usuarioRepository.findByUsername(dto.getUsername());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
                UsuarioDTO resposta = new UsuarioDTO();
                resposta.setCodusu(usuario.getCodusu());
                resposta.setNome(usuario.getNome());
                resposta.setUsername(usuario.getUsername());
                return resposta;
            }
        }

        return null;
    }

    public Usuario buscarPorId(Long codusu) {
        return usuarioRepository.findById(codusu).orElse(null);
    }
}
