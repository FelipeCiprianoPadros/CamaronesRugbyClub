package com.camarones.clubManagment.controller;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Operador;
import com.camarones.clubManagment.service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operador")
@CrossOrigin("*")
public class OperadorController {

    @Autowired
    private OperadorService os;

    @GetMapping("")
    public List<Operador> GetAll(){
        return (List<Operador>) os.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveOperador(@RequestBody Operador operador){
        return os.SaveOperador(operador);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity UpdateSocio(@PathVariable int id,@RequestBody Operador operador){
        return os.UpdateOperador(id,operador);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity DeleteOperador(@PathVariable int id){
        return os.DeleteOperador(id);
    }

}