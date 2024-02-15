package com.projetojpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetojpa.dto.UsuarioRecord;
import com.projetojpa.entities.Usuario;
import com.projetojpa.repository.UsuarioRepository;
@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public UsuarioRecord salvar(UsuarioRecord usuarioRecord) {
		Usuario usuario = new Usuario(usuarioRecord.nome(), usuarioRecord.senha());
		Usuario salvarUsuario = usuarioRepository.save(usuario);
		return new UsuarioRecord(salvarUsuario.getId(), salvarUsuario.getNome(), salvarUsuario.getSenha());
		
	}
	
	public UsuarioRecord atualizar(Long id, UsuarioRecord usuarioRecord) {
		Usuario existeUsuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
		
		existeUsuario.setNome(usuarioRecord.nome());
		existeUsuario.setSenha(usuarioRecord.senha());
		
		Usuario updateUsuario = usuarioRepository.save(existeUsuario);
		return new UsuarioRecord(updateUsuario.getId(), updateUsuario.getNome(), updateUsuario.getSenha());
		
	}
	
	public boolean deletar(Long id) {
		Optional <Usuario> existeUsuario = usuarioRepository.findById(id);
		if (existeUsuario.isPresent()) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
		}
	public Usuario buscarPorId(Long id) {
		Optional <Usuario> usuario = usuarioRepository.findById(id);
		return usuario.orElse(null);
	}
}
