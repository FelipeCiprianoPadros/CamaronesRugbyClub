package com.camarones.clubManagment.service;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class CuotaService {

    @Autowired
    private final CuotaRepository cr;

    public CuotaService(CuotaRepository cr){
        this.cr = cr;
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

    public void AplicarRecargoCuota(){
        try{
            cr.AplicarRecargoCuotasVencidas();
        }
        catch (Exception e){
            // aca deberiamos de agregar algo
        }
    }

}
