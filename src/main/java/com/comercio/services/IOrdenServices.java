package com.comercio.services;

import com.comercio.model.Orden;
import com.comercio.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IOrdenServices {

    public Orden crear(Orden orden);

    public void actualizar(Orden orden);

    public void borrar(Integer id);

    public Optional<Orden> ordenId(Integer id);

    List<Orden> mostrar();

    public String generarNumOrden();

    List<Orden>  findByUsuario (Usuario usuario);
}
