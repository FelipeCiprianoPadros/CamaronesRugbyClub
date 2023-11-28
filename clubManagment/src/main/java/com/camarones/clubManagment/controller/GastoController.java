package com.camarones.clubManagment.controller;

import com.camarones.clubManagment.model.Gasto;
import com.camarones.clubManagment.model.Proovedor;
import com.camarones.clubManagment.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gasto")
@CrossOrigin("*")
public class GastoController {

    @Autowired
    private GastoService gs;

    @GetMapping("")
    public List<Gasto> GetAll(){
        return (List<Gasto>) gs.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveGasto(@RequestBody Gasto gasto){
        return gs.SaveGasto(gasto);
    }
    @PostMapping("/{id}/update")
    public ResponseEntity UpdateGasto(@PathVariable int id, @RequestBody Gasto gasto){
        return gs.UpdateGasto(id, gasto);
    }
    @PostMapping("/{id}/delete")
    public ResponseEntity DeleteGasto(@PathVariable int id){
        return gs.DeleteGasto(id);
    }
    @GetMapping("")
    public ResponseEntity ObtenerGastoXFecha(@RequestParam Date fecha){
        return gs.ObtenerGastoXFecha(fecha);
    }
    @GetMapping("")
    public ResponseEntity ObtenerGastoXmes(@RequestParam String mes){
        return gs.ObtenerGastoXMes(mes);
    }
    @GetMapping("")
    public ResponseEntity ObtenerGastoXFechaYProovedorDeterminado(@RequestParam Date fecha, @RequestBody Proovedor proovedor){
        return gs.ObtenerGastoXFechaYProovedorDeterminado(fecha, proovedor);
    }
    @GetMapping("")
    public ResponseEntity ObtenerGastosEntreFechas(@RequestParam Date fechaInicio, @RequestParam Date fechafinal){
        return gs.ObtenerGastosEntreFechas(fechaInicio,fechafinal);
    }
    @GetMapping("")
    public ResponseEntity ObtenerGastosTotalesPorProovedor(@RequestBody Proovedor proovedor){
        return gs.ObtenerGastosTotalesPorProovedor(proovedor);
    }

}
