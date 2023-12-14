package com.camarones.clubManagment.service;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Operador;
import com.camarones.clubManagment.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class OperadorService {

    @Autowired
    private final OperadorRepository or;

    public OperadorService(OperadorRepository or){
        this.or = or;
    }
    public ResponseEntity<List<Operador>> getAll(){
        List listaOperador = new ArrayList<Operador>();
        try{
            listaOperador.add(or.findAll());
            return new ResponseEntity<>(listaOperador, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveOperador (Operador operador){
        try{
            or.save(operador);
            return ResponseEntity.status(CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateOperador (int id, Operador operador){
        try{
            if (or.existsById(id)) {
                Operador ope = or.findById(id).get();
                ope.setUserName(operador.getUserName());
                ope.setContrasenia(operador.getContrasenia());
                ope.setPermisoTotal(operador.isPermisoTotal());
                ope.setContacto(operador.getContacto());
                or.save(ope);
                return ResponseEntity.status(OK).build();
            }
            else{
                return ResponseEntity.status(NOT_FOUND).build();
            }

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteOperador (int id){
        try{
            if (or.existsById(id)){
                or.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }


}
