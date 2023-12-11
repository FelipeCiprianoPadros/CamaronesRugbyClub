package com.camarones.clubManagment.service;


import com.camarones.clubManagment.MediatorPackage.Mediator;
import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.repository.SocioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SocioService {
    private final SocioRepository sr;
    private final Mediator mediator;

    public SocioService(SocioRepository sr, Mediator mediator){
        this.sr = sr;
        this.mediator = mediator;
    }

    public ResponseEntity<List<Socio>> getAll(){
        List listaSocios = new ArrayList<Socio>();
        try{
            listaSocios.add(sr.findAll());
            return new ResponseEntity<>(listaSocios, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Socio>> getAllByDiciplina(String diciplina){
        /*
        Este metodo se encarga de buscar socios por una diciplina, en la consulta de la base de datos
        se encarga de pasar a minusculas todo.
         */
        List listaSocios = new ArrayList<Socio>();
        try{
            listaSocios.add(sr.buscarPorDisciplina(diciplina));
            return new ResponseEntity<>(listaSocios, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveSocio (Socio socio){
        try{
            sr.save(socio);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateSocio (int id, Socio socio){
        try{
            if (sr.existsById(id)) {
                Socio soc = sr.findById(id).get();
                soc.setNombre(socio.getNombre());
                soc.setApellido(socio.getApellido());
                soc.setContacto(socio.getContacto());
                soc.setFechaNacimiento(socio.getFechaNacimiento());
                soc.setDescuentoCuota(socio.getDescuentoCuota());
                soc.setCategoria(socio.getCategoria());
                sr.save(soc);
                return ResponseEntity.status(OK).build();
            }
            else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteSocio (int id){
        try{
            if (sr.existsById(id)){
                sr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Scheduled(cron = "0 0 5 1 * ?")
    public ResponseEntity AplicarDescuentoCuota(Socio socio, int id){
        /*
        Este metodo recibe como parametro un socio y un id, para asi ubicarlo en la base de datos
        y poder aplicarle su respectivo descuento a las cuotas.
         */
        try{
            if (socio.getDescuentoCuota() > 0 && socio.getDescuentoCuota() <= 100){
                /*
                Se verifica que los valores que tiene asociados al atibuto descuentoCuota esten dentro
                de los parametros acertados.
                 */
                if (sr.existsById(id)){
                    /*
                    Se verifica que el socio exista en la base de datos.
                     */
                    List<Cuota> cuotasSocio = socio.getCuotas(); // Se obtienen las cuotas del socio.
                    if (cuotasSocio.isEmpty()){
                        // Evaluo que la lista no este vacia, ya que de lo contrario no entraria dentro del bucle y me arrojaria un status "ok".
                        return ResponseEntity.status(BAD_REQUEST).body("No se encontraron cuotas vinculadas al socio");
                    }
                    else{
                        for (Cuota cuota: cuotasSocio) {
                            /*
                            Itero sobre las cuotas del socio en busca de la cuota de la del mes actual
                            para aplicarle el descuento correspondiente.
                             */
                            if (cuota.getMesCuota().equalsIgnoreCase(ObtenerMesActual()) && !cuota.isPagada()) {
                                // Se verifica que el mes de la cuota sea el mismo que el actual ademas se verifica que no haya sido pagada, que realmente es inecesario pero por las dudas.
                                cuota.setPrecioCuota((cuota.getPrecioCuota() * socio.getDescuentoCuota()) / 100.0);
                                mediator.ComunicarParaDescuento(cuota, cuota.getId()); // Aca le paso la cuota ya con el precio actualizo y su id
                            } else {
                                return ResponseEntity.status(BAD_REQUEST).body("No se encontro la cuota dentro el sistema");
                            }
                        }
                    }
                    return ResponseEntity.status(OK).build();

                }else
                    return ResponseEntity.status(NOT_FOUND).build();
            }else
                return ResponseEntity.status(BAD_REQUEST).body("Los parametros ingresador para realizar el descuento son incorrectos.");
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Socio>> BuscarSocioPorCategoria(String categoria){
        /*
        Este metodo recibe como parametro una categoria y retorna una lista con los socios dependiendo de
        la categoria seleccionada
         */
        try{
            List<Socio> socios =  sr.BuscarSocioPorCategoria(categoria);
            if(socios.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(socios, OK);
        }
        catch ( Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public String ObtenerMesActual(){
        /*
        Este metodo se encarga de obtener el mes actual en formato string por ej: "diciembre"
         */
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM");
        String mesActual = formatoMes.format(fechaActual);
        return mesActual;
    }

}
