package com.camarones.clubManagment.MediatorPackage;

import com.camarones.clubManagment.model.Cuota;

public interface Mediator {
    void ComunicarParaDescuento(Cuota cuota, int id);
    void ComunicarParaobtenerSocios();

    String comunicarParaObtenerDivision();
}
