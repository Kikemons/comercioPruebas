package com.comercio.services;


import com.comercio.model.Orden;
import com.comercio.model.Usuario;
import com.comercio.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServices implements IOrdenServices{

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public Orden crear(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public void actualizar(Orden orden) {
    ordenRepository.save(orden);
    }

    @Override
    public void borrar(Integer id) {
    ordenRepository.deleteById(id);
    }

    @Override
    public Optional<Orden> ordenId(Integer id) {
        return ordenRepository.findById(id);
    }

    @Override
    public List<Orden> mostrar() {
        return ordenRepository.findAll();
    }

    @Override
    public String generarNumOrden() {
        int num=0;
        String numeroC="";

        List<Orden> ordenes=mostrar();
        List<Integer>numeros= new ArrayList<Integer>();
        ordenes.stream().forEach(o ->numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            num=1;
        }else{
            num=numeros.stream().max(Integer::compare).get();
            num++;
        }

        if (num<10){
            numeroC="000000000"+String.valueOf(num);
        }else if(num<100){
            numeroC="00000000"+String.valueOf(num);
        }else if(num<1000){
            numeroC="0000000"+String.valueOf(num);
        }else if(num<10000){
            numeroC="000000"+String.valueOf(num);
        }


        return numeroC;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }
}
