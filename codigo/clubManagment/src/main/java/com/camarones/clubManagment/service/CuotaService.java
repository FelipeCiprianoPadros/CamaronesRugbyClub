package com.camarones.clubManagment.service;

import com.camarones.clubManagment.MediatorPackage.Mediator;
import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.repository.CuotaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class CuotaService {

    private final CuotaRepository cr;
    private final Mediator mediator;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class); // para ver los errores de los @Scheduled

    @Autowired // Deje la notacion aca para facilitarte el testing
    public CuotaService(CuotaRepository cr, Mediator mediator){
        this.cr = cr;
        this.mediator = mediator;
    }

    public ResponseEntity<List<Cuota>> getAll(){
        List<Cuota> listaCuotas = new ArrayList<>();
        try{
            listaCuotas.addAll((Collection) cr.findAll());
            return new ResponseEntity<>(listaCuotas, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveCuota(Cuota cuota){
        try{
            Cuota cuotaGuardada = cr.save(cuota);
            if (cuotaGuardada != null){
                // Es muy raro que devuelva null pero no me cierro a la posibilidad.
                return ResponseEntity.status(CREATED).build();
            }
            else{
                return ResponseEntity.status(BAD_REQUEST).build();
                }
            }
            catch (Exception e){
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            }
    }

    public ResponseEntity UpdateCuota (int id, Cuota cuota){
        try {
            if (cr.existsById(id)) {
                Cuota cta = cr.findById(id).get();
                cta.setMesCuota(cuota.getMesCuota());
                cta.setFechaVencimiento(cuota.getFechaVencimiento());
                cta.setPagada(cuota.isPagada());
                cta.setPrecioCuota(cuota.getPrecioCuota());
                cr.save(cta);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
            }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteCuota (int id){
        try{
            if (cr.existsById(id)){
                cr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Scheduled(cron = "0 0 0 10 * ?") // esto supuestamente te ejecuta automaticamente las funciones.
    public void AplicarRecargoCuota() throws Exception {
        try{
            cr.AplicarRecargoCuotasVencidas();
        }
        catch (Exception e){
            throw new Exception("Ocurrio un error al aplicar el recargo");
        }
    }
    @Scheduled(cron = "0 0 0 1 * ?") // Asignada para que pase a las 00:00 del 1 de cada mes
    public void AsignarCuotaAsocios(List<Socio> socios, String mes){ // No estoy seguro de que deba de ser publico este metodo
        /*
        Este metodo se encarga de asignarle las cuotas de cada mes a todos los socios.
         */
        try{
            if(!socios.isEmpty()){
                // Primero verifico que el listado de socios que este recibiendo no este vacio
                for (Socio socio: socios){
                    // Luego itero sobre ada socio
                    LocalDate fechaActual = LocalDate.now();
                    int añoActual = fechaActual.getYear();
                    int mesActual = fechaActual.getMonthValue();
                    LocalDate fecha = LocalDate.of(añoActual, mesActual, 10);
                    Date fechaVencimiento = Date.valueOf(fecha);
                    // Lineas 100, 101, 102, 103, 104 son para obtener la fecha de vencimiento, el 10 de cada mes
                    Cuota cuotaNueva = new Cuota(null,mes,fechaVencimiento,false,000,0,socio); // tendriamos que ver como dejar un valor fijo a menos de que se modifique.
                    /*
                    Creo una nueva cuota, fijate que estoy iterando sobre la lista de socios que obtengo de su metodo getAll()
                    es decir que todos los socios por los que voy pasando ya existen en la base de datos.
                     */
                    cr.save(cuotaNueva); // Guardo la cuota en la base de datos
                }
            }else{
                throw new Error("no se pudo asignar las cuotas a los socios");
                // Esto si se ve en el front.
            }
        }
        catch (Exception ex){
            logger.info("se produjo el siguiente error: " + ex.getMessage());
        }
    }
    public ResponseEntity PagarCuota(Cuota cuota, double monto){
        /*
        Este metodo se encarga de dar como pagada cuota, recibiendo la cuota que se quiere pagar
        y un monto que sera descontado a la cuota.
         */
        try{
            if(cr.existsById(cuota.getId())){
                // Verifico que la cuota exista dentro de la base de datos
                if (monto < cuota.getPrecioCuota() && monto > 0){
                    // Verifico que el monto final sea menor al precio de la cuota
                    // de lo contrario estaria pagando mal y generaria inconsistencias en la base de datos

                    cuota.setPagada(true);
                    cuota.setCantidadPagada(monto);

                    UpdateCuota(cuota.getId(), cuota); // cambio los valores en la base de datos

                    return ResponseEntity.status(OK).build();
                }else{

                    return ResponseEntity.status(NOT_ACCEPTABLE).build();
                }
            }
            else{
                // Si no se encontro retorno not found
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
