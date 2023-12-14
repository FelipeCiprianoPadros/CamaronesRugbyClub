package com.camarones.clubManagment.MediatorPackage;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.service.CuotaService;
import com.camarones.clubManagment.service.SocioService;

import java.util.List;

public class ConcreteMediator implements Mediator{

    private  final SocioService socioService;
    private final CuotaService cuotaService;

    public ConcreteMediator(SocioService socioService, CuotaService cuotaService) {
        this.socioService = socioService;
        this.cuotaService = cuotaService;
    }
    @Override
    public void ComunicarParaDescuento(Cuota cuota, int id) {
        /*
        Poder ver Ciro que de lo unico que se encarga el mediator es de comunicar estos servicios,
        se podria hacer directamente entre los 2 servicio y listo pero de esta manera podemos hacerlo con mas servicio
        de ser necesario en un futuro.
         */
        cuotaService.UpdateCuota(id, cuota);
    }

    @Override
    public void ComunicarParaobtenerSocios(){
        List socios = (List) socioService.getAll();
        cuotaService.AsignarCuotaAsocios(socios, socioService.ObtenerMesActual());
    }

    @Override
    public String comunicarParaObtenerDivision() {
        return null;
    }
}
