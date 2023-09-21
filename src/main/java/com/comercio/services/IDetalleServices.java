package com.comercio.services;

import com.comercio.model.DetalleDeOrden;

import java.util.List;
import java.util.Optional;

public interface IDetalleServices {


    public DetalleDeOrden crear(DetalleDeOrden detalleDeOrden);

    public void actualizar(DetalleDeOrden detalleDeOrden);

    public void borrar(Integer id);

    public Optional<DetalleDeOrden> ordenId(Integer id);

    List<DetalleDeOrden > mostrar();

}
