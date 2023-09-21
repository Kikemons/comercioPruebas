package com.comercio.repository;

import com.comercio.model.DetalleDeOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepository extends JpaRepository<DetalleDeOrden, Integer> {
}
