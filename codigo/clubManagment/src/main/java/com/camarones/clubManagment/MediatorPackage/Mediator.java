package com.camarones.clubManagment.MediatorPackage;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Socio;

import java.util.List;

public interface Mediator {
    void ComunicarParaDescuento(Cuota cuota, int id);
    void ComunicarParaobtenerSocios();
}
