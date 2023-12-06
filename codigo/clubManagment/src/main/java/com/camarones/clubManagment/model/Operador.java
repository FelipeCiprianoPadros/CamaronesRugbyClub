package com.camarones.clubManagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operador {

    @Id
    private Integer id;

    private String userName;

    private String contrasenia;

    private boolean permisoTotal;

    private String contacto;
}
