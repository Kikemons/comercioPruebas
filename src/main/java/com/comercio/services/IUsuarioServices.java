package com.comercio.services;

import com.comercio.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServices {

  public Usuario crear(Usuario usuario);

  public Optional<Usuario> obtenerId(Integer id);

  public void borrar(Integer id);

  List<Usuario> mostrarUsuarios();

  public void actualizar(Usuario usuario);

  public Optional<Usuario> findByEmail(String email);

}
