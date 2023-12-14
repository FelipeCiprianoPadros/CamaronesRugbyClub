package com.camarones.clubManagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operador {

    @Id
    private Integer id;
    @NotNull
    private String userName;
    @NotNull
    private String contrasenia;
    @NotNull
    private boolean permisoTotal;
    @NotNull
    private String contacto;

}
