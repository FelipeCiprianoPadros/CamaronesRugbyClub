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
    public List<Gasto> getAll() {
        return (List<Gasto>) gs.getAll();
    }

    @PostMapping("")
    public ResponseEntity saveGasto(@RequestBody Gasto gasto) {
        return gs.SaveGasto(gasto);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity updateGasto(@PathVariable int id, @RequestBody Gasto gasto) {
        return gs.UpdateGasto(id, gasto);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity deleteGasto(@PathVariable int id) {
        return gs.DeleteGasto(id);
    }

    @GetMapping("/porFecha")
    public ResponseEntity obtenerGastoXFecha(@RequestParam Date fecha) {
        return gs.ObtenerGastoXFecha(fecha);
    }

    @GetMapping("/porMes")
    public ResponseEntity obtenerGastoXMes(@RequestParam String mes) {
        return gs.ObtenerGastoXMes(mes);
    }

    @GetMapping("/porFechaYProovedor")
    public ResponseEntity obtenerGastoXFechaYProovedorDeterminado(
            @RequestParam Date fecha, @RequestParam Proovedor proveedor) {
        return gs.ObtenerGastoXFechaYProovedorDeterminado(fecha, proveedor);
    }

    @GetMapping("/entreFechas")
    public ResponseEntity obtenerGastosEntreFechas(
            @RequestParam Date fechaInicio, @RequestParam Date fechaFinal) {
        return gs.ObtenerGastosEntreFechas(fechaInicio, fechaFinal);
    }

    @GetMapping("/totalPorProveedor")
    public ResponseEntity obtenerGastosTotalesPorProovedor(@RequestParam Proovedor proveedor) {
        return gs.ObtenerGastosTotalesPorProovedor(proveedor);
    }
    /*
    NOTA: cambiar los parametros que reciben proveedores como parametro por sus id, o mas bien
    revisar cual es mas conveniente
     */
}
