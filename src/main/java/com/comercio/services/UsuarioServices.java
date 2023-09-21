package com.comercio.services;

import com.comercio.model.Usuario;
import com.comercio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices implements IUsuarioServices{

    @Autowired
   private  UsuarioRepository usuarioRepository;

    @Override
    public Usuario crear(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> obtenerId(Integer id){
        return usuarioRepository.findById(id);
    }

    @Override
    public void borrar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> mostrarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void actualizar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
