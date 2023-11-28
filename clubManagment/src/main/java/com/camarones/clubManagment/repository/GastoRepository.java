package com.camarones.clubManagment.repository;

import com.camarones.clubManagment.model.Gasto;
import com.camarones.clubManagment.model.Proovedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GastoRepository extends CrudRepository<Gasto,Integer> {
    @Query("SELECT g FROM Gasto g WHERE DATE(g.fecha) = DATE(:fecha)")
    List<Gasto> findGastoByFecha(@Param("fechaParametro") Date fecha);

    //Separacion por legibilidad

    @Query("SELECT g FROM Gasto g WHERE MONTH(g.fecha) = MONTH(:fechaInicioMes)")
    List<Gasto> findGastoByMes(@Param("fechaInicioMes") Date fechaInicioMes);

    //Separacion por legibilidad
    @Query("SELECT g FROM Gasto g WHERE g.proveedor = :proveedor AND DATE(g.fecha) = DATE(:fecha)")
    List<Gasto> findByGastoProveedorAndFecha(@Param("proveedor") Proovedor proovedor, @Param("fecha") Date fecha);

    //Separacion por legibilidad
    @Query("SELECT g FROM Gasto g WHERE g.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Gasto> findGastosEntreFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    //Separacion por legibilidad
    @Query("SELECT g FROM Gasto g WHERE g.proveedor = :proveedor")
    List<Gasto> findGastosByProveedor(@Param("proveedor") Proovedor proovedor);
}
