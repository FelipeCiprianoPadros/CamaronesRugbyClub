package com.camarones.clubManagment.controller;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuota")
@CrossOrigin("*")
public class CuotaController {

    @Autowired
    private CuotaService cs;

    @GetMapping("")
    public List<Cuota> GetAll() {
        return (List<Cuota>) cs.getAll();
    }

    @PostMapping("")
    public ResponseEntity SaveCuota(@RequestBody Cuota cuota) {
        return cs.SaveCuota(cuota);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity UpdateCuota(@PathVariable int id, @RequestBody Cuota cuota) {
        return cs.UpdateCuota(id, cuota);
    }

    @PostMapping("/{id}/eliminar")
    public ResponseEntity DeleteCuota(@PathVariable int id) {
        return cs.DeleteCuota(id);
    }

    @PostMapping("")
    public ResponseEntity pagarCuota(@RequestBody Cuota cuota, @RequestParam double monto) {
        return cs.PagarCuota(cuota, monto);
    }
}