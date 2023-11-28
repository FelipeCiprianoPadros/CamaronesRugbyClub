package com.camarones.clubManagment.repository;

import com.camarones.clubManagment.model.Operador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends CrudRepository<Operador, Integer> {
}
