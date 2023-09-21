package com.comercio.services;

import com.comercio.model.DetalleDeOrden;
import com.comercio.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleServices implements IDetalleServices {

    @Autowired
    private DetalleRepository detalleRepository;

    @Override
    public DetalleDeOrden crear(DetalleDeOrden detalleDeOrden) {
        return detalleRepository.save(detalleDeOrden);
    }

    @Override
    public void actualizar(DetalleDeOrden detalleDeOrden) {

    }

    @Override
    public void borrar(Integer id) {

    }

    @Override
    public Optional<DetalleDeOrden> ordenId(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<DetalleDeOrden> mostrar() {
        return null;
    }
}
