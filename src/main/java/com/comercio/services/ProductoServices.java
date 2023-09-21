package com.comercio.services;

import com.comercio.model.Producto;
import com.comercio.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoServices implements IproductoServices{

    @Autowired
    private ProductoRepository productoRepository;



    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> obtener(Integer id) {
       return productoRepository.findById(id);
    }

    @Override
    public void actualizar(Producto producto) {
    productoRepository.save(producto);
    }

    @Override
    public void borrar(Integer id) {
    productoRepository.deleteById(id);
    }


    @Override
    public List<Producto> mostrarProductos() {
        return productoRepository.findAll();
    }


}
