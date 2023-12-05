package com.camarones.clubManagment.controller;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socio")
@CrossOrigin("*")
public class SocioController {

    @Autowired
    private SocioService ss;

    @GetMapping("")
    public List<Socio> GetAll(){
        return (List<Socio>) ss.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveSocio(@RequestBody Socio socio){
        return ss.SaveSocio(socio);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity UpdateSocio(@PathVariable int id,@RequestBody Socio socio){
        return ss.UpdateSocio(id,socio);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity DeleteSocio(@PathVariable int id){
        return ss.DeleteSocio(id);
    }
    @PostMapping("")
    public List<Socio> BuscarSocioPorCategoria(@RequestParam String categoria){
        return (List<Socio>) ss.BuscarSocioPorCategoria(categoria);
    }

}
