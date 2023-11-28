package com.camarones.clubManagment.repository;

import com.camarones.clubManagment.model.Cuota;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CuotaRepository extends CrudRepository<Cuota, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Cuota c SET c.precioCuota = c.precioCuota * 1.1 WHERE c.fechaVencimiento < CURDATE() AND c.pagada = 0")
    int AplicarRecargoCuotasVencidas();
}
