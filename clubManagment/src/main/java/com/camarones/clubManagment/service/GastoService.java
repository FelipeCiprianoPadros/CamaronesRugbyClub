package com.camarones.clubManagment.service;
import com.camarones.clubManagment.model.Gasto;
import com.camarones.clubManagment.model.Proovedor;
import com.camarones.clubManagment.repository.GastoRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GastoService {
    @Autowired
    private final GastoRepository gr;

    private List<Gasto> gastos;

    public GastoService(GastoRepository gr){
        this.gr = gr;
        this.gastos = new ArrayList<Gasto>();
    }

    public ResponseEntity<List<Gasto>> getAll(){
        List listaGastos = new ArrayList<Gasto>();
        try{
            listaGastos.add(gr.findAll());
            if(listaGastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(listaGastos, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity SaveGasto(Gasto gasto){
        try{
            gr.save(gasto);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateGasto(int id, Gasto gasto){
        try{
            if (gr.existsById(id)) {
                Gasto g = gr.findById(id).get();
                g.setNombreGasto(gasto.getNombreGasto());
                g.setDetalleGasto(gasto.getDetalleGasto());
                g.setMontoGasto(gasto.getMontoGasto());
                g.setMetodoPago(gasto.getMetodoPago());
                g.setFecha(gasto.getFecha());
                g.setProovedor(gasto.getProovedor());
                gr.save(g);
                return ResponseEntity.status(OK).build();
            }
            else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteGasto(int id){
        try{
            if (gr.existsById(id)){
                gr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXFecha(Date fecha){
        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findGastoByFecha(fecha);
            if (gastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXMes(String mes){
        /*
        Bien lo que tenia planeado en esta funcion es que marquen el mes y al recibir
        un mes por parametro vamos a determinarle una fecha y darle los gastos de ese mes
         la logica que vo a aplicar deberia de ser correcta
         */
        try{
            Date fechaInicioMes = ConvertirMesADate(mes);

            return new ResponseEntity<>(gr.findGastoByMes(fechaInicioMes), OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXFechaYProovedorDeterminado(Date fecha, Proovedor proovedor){
        /*
        Esta funcion tenia planeado que devuelva un objeto pero se me ocurrio que a un proveedor por X motivo se le podian llegar a hacer
        2 compras distintas en un dia es poco problable pero prefiero preevenir.
         */
        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findByGastoProveedorAndFecha(proovedor, fecha);
            if(gastos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastosEntreFechas(Date fechaInicial, Date fechaFinal){
        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findGastosEntreFechas(fechaInicial, fechaFinal);
            if(gastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastosTotalesPorProovedor(Proovedor proovedor){

        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findGastosByProveedor(proovedor);
            if(gastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private Date ConvertirMesADate(String mes){
        /*
        En teoria esto deberia de funcionar. ojala
         */
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
            Date fecha = sdf.parse(mes);
            return fecha;
        }
        catch (ParseException p){
            throw  new IllegalArgumentException("formato de mes no valido" + mes);
        }
    }

    private List<Gasto> vaciarLista(){
        if(this.gastos.isEmpty()){
            return this.gastos;
        }
        else{
            this.gastos.clear();
            return this.gastos;
        }
    }
}
