package com.camarones.clubManagment.service;


import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SocioService {

    @Autowired
    private final SocioRepository sr;

    public SocioService(SocioRepository sr){
        this.sr = sr;
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
    public ResponseEntity AplicarDescuentoCuota(Socio socio, int id){
        try{
            if (socio.getDescuentoCuota() > 0 && socio.getDescuentoCuota() <= 100){
                if (sr.existsById(id)){
                    

                return ResponseEntity.status(OK).build();
                }else
                    return ResponseEntity.status(NOT_FOUND).build();
            }else
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Socio>> BuscarSocioPorCategoria(String categoria){

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

}
