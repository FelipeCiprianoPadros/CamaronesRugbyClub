package com.camarones.clubManagment.controller;

import com.camarones.clubManagment.model.Proovedor;
import com.camarones.clubManagment.model.Socio;
import com.camarones.clubManagment.service.ProovedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/proovedor")
public class ProovedorController{
    @Autowired
    private ProovedorService ps;

    @GetMapping("")
    public List<Proovedor> GetAll(){
        return (List<Proovedor>) ps.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveProovedor(@RequestBody Proovedor proovedor){
        return ps.SaveProovedor(proovedor);
    }
    @PostMapping("/{id}/update")
    public ResponseEntity UpdateProovedor(@PathVariable int id,@RequestBody Proovedor proovedor){
        return ps.UpdateProovedor(id,proovedor);
    }
    @PostMapping("/{id}/delete")
    public ResponseEntity DeleteProovedor(@PathVariable int id){
        return ps.DeleteProovedor(id);
    }
}
