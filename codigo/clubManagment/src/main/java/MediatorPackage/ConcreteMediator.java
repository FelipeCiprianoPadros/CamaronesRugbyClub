package MediatorPackage;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.repository.SocioRepository;
import com.camarones.clubManagment.service.CuotaService;
import com.camarones.clubManagment.service.SocioService;

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
}
