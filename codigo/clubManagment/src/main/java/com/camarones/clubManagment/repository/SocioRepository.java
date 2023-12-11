package com.camarones.clubManagment.repository;


import com.camarones.clubManagment.model.Socio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends CrudRepository<Socio, Integer> {
    @Query("SELECT s FROM Socio s WHERE s.categoria LIKE %:categoriaSocio%")
    List<Socio> BuscarSocioPorCategoria(@Param("categoriaSocio") String categoriaSocio);

    // Separacion por legibilidad

    @Query("SELECT s FROM Socio s WHERE LOWER(s.disciplina) LIKE LOWER(CONCAT('%', :disciplina, '%'))")
    List<Socio> buscarPorDisciplina(@Param("disciplina") String disciplina);
}
