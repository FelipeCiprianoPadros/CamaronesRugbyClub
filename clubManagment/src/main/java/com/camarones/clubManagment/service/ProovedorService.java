package com.camarones.clubManagment.service;

import com.camarones.clubManagment.model.Proovedor;
import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.repository.ProovedorRepossitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProovedorService {
    @Autowired
    private final ProovedorRepossitory pr;

    public ProovedorService(ProovedorRepossitory pr){
        this.pr = pr;
    }
    public ResponseEntity<List<Proovedor>> getAll(){
        List listaProovedores = new ArrayList<Proovedor>();
        try{
            listaProovedores.add(pr.findAll());
            return new ResponseEntity<>(listaProovedores, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity SaveProovedor(Proovedor proovedor){
        try{
            pr.save(proovedor);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateProovedor(int id, Proovedor proovedor){
        try{
            if (pr.existsById(id)) {
                Proovedor pro = pr.findById(id).get();
                pro.setNombre(pro.getNombre());
                pro.setCategoria(pro.getCategoria());
                // NO SE SI ES NECESARIO RE ASIGNAR LA LISTA DE GASTOS POR LAS DUDAS NO LO HAGO
                pr.save(pro);
                return ResponseEntity.status(OK).build();
            }
            else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteProovedor(int id){
        try{
            if (pr.existsById(id)){
                pr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
