package com.camarones.clubManagment.service;

import MediatorPackage.ConcreteMediator;
import MediatorPackage.Mediator;
import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class CuotaService {

    private final CuotaRepository cr;
    private final Mediator mediator;
    @Autowired // Deje la notacion aca para facilitarte el testing
    public CuotaService(CuotaRepository cr, Mediator mediator){
        this.cr = cr;
        this.mediator = mediator;
    }

    public ResponseEntity<List<Cuota>> getAll(){
        List listaCuotas = new ArrayList<Cuota>();
        try{
            listaCuotas.add(cr.findAll());
            return new ResponseEntity<>(listaCuotas, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveCuota(Cuota cuota){
        try{
            cr.save(cuota);
            return ResponseEntity.status(CREATED).build();
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

}
