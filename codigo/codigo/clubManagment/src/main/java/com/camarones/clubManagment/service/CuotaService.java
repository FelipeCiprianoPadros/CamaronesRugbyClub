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

    @Scheduled(cron = "0 0 0 10 * ?")
    public void AplicarRecargoCuota() throws Exception {
        /*
        Este metodo se encarga de aplicar recargo a las cuotas
         */
        try{
            cr.AplicarRecargoCuotasVencidas();
        }
        catch (Exception e){
            throw new Exception("Ocurrio un error al aplicar el recargo");
        }
    }
    @Scheduled(cron = "0 0 0 1 * ?") // Asignada para que pase a las 00:00 del 1 de cada mes
    public void AsignarCuotaAsocios(List<Socio> socios, String mes){
        /*
        Este metodo se encarga de asignarle las cuotas de cada mes a todos los socios.
         */
        try{
            LocalDate fechaActual = LocalDate.now();
            int añoActual = fechaActual.getYear();
            int mesActual = fechaActual.getMonthValue();
            LocalDate fecha = LocalDate.of(añoActual, mesActual, 10);
            Date fechaVencimiento = Date.valueOf(fecha);
            // Lineas 111, 112, 113, 114, 115 son para obtener la fecha de vencimiento, el 10 de cada mes
            double precio = 0.0;
            if(!socios.isEmpty()){
                // Primero verifico que el listado de socios que este recibiendo no este vacio
                for (Socio socio: socios){
                    // Luego itero sobre cada socio
                    switch (socio.getDiciplina()){
                        // ahora utilizo el switch para verificar su diciplina
                        case "rugby":
                            precio = obtenerPrecioRugby(socio.getDivision());
                            if (precio >= 0.0 ){
                                // Verifico que el precio este bien asignado, ya que no quiero precios negativos.
                                Cuota cuotaNuevaRugby = new Cuota(null,mes,fechaVencimiento,false,precio,0,socio);
                                cr.save(cuotaNuevaRugby);
                            }
                            else{
                                throw  new Error("Error al asignar el precio, el precio debe de ser mayor o igual a cero y es de: " + precio);
                            }
                            break;
                        case "hockey":
                            precio = obtenerPrecioHockey(socio.getDivision());
                            if (precio >= 0.0 ){
                                Cuota cuotaNuevaHockey = new Cuota(null,mes,fechaVencimiento,false,precio,0,socio);
                                cr.save(cuotaNuevaHockey);
                            }
                            else{
                                throw  new Error("Error al asignar el precio, el precio debe de ser mayor o igual a cero y es de: " + precio);
                            }
                            break;
                        default:
                            // por las dudas arrojo un error pero no deberia de ocurrir por lo que explico debajo
                            throw new Error("No se encontro la disciplina.");
                            /*
                            Las diciplinas deben de ser de "hardCodeadas" por asi decirlo o a lo que me refiero es que
                            el operador no podra escribir la diciplina debera de elegir entre las opciones que se le de.
                            y las opciones seran hockey o rugby todo en minuscula.
                             */
                    }

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
                    // Verifico que el monto final sea menor al precio de la cuota y mayor a 0 de lo contrario podria generar inconsistencias en la base de datos


                    cuota.setPagada(true);
                    cuota.setCantidadPagada(monto);

                    UpdateCuota(cuota.getId(), cuota); // cambio los valores en la base de datos

                    return ResponseEntity.status(OK).build();
                }else{
                    // Si el monto no cumple los requisitos le arrojo el siguiente status.
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

    private double obtenerPrecioRugby(String division){
        /*
        Esta funcion se encarga de retornar el precio correspondiente en base a una categoria de la disciplina "rugby"
        recibe como parametro una division y devuelve un tipo de dato double con los precios para la disciplina de RUGBY.
         */
        switch (division){
            // Hay que establecer los precios.
            case "escuelita":
                return 0.0;
            case "infantiles":
                return 0.0;
            case "juveniles":
                return 0.0;
            case "plantel superior":
                return 0.0;
            case "veterano":
                return 0.0;

            default:
                // Se devolvera 0.0 en caso de que haya un error lo que no deberia de pasar, pero tal error se maneja en la funcion AsignarCuotaSocio().
                return 0.0;
        }
    }

    private double obtenerPrecioHockey(String division){
                /*
        Esta funcion se encarga de retornar el precio correspondiente en base a una categoria de la disciplina "hockey"
        recibe como parametro una division y devuelve un tipo de dato double con los precios para la disciplina de HOCKEY.
         */
        switch (division){
            // Hay que establecer los precios.
            case "escuelita":
                return 0.0;
            case "infantiles":
                return 0.0;
            case "juveniles":
                return 0.0;
            case "plantel superior":
                return 0.0;
            default:
                return 0.0;
        }
    }
}
