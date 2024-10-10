package com.boostmyfool.beastore.repositories;

import com.boostmyfool.beastore.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Productos,Integer> {
}
