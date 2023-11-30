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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GastoService {
    @Autowired
    private final GastoRepository gr;

    private List<Gasto> gastos; // Esta lista esta sirve para no tener que instanciar tantas listas dentro de las funciones internamente

    public GastoService(GastoRepository gr){
        this.gr = gr;
        this.gastos = new ArrayList<>();
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
        /*
        Esta funcion recibe como parametro una fecha y retorna los gastos realizados
        en tal fecha.
         */
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
            Esta funcion recibe como parametro un mes que podria ser "enero" por ej
            y va retornar una lista con todos los gastos registrados desde el principio al
            fin de tal mes.
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
           Esta funcion recibe como parametros una fecha y proveedor determinado retornando
           una lista con todos los gastos asociados a tal proveedor determinados en esa fecha.
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
        /*
        Esta funcion recibe como parametro 2 fechas una de inicio y otra de finalizacion y
        se encarga de retornar todos los gastos realizados entre esas 2 fechas.
         */
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
        /*
        Esta funcion recibe un proveedor como parametro y retornanara todos los gastos
        relacionados a ese proveedor.
         */
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

    private static Date ConvertirMesADate(String mes) {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMMM");
            Date fecha = formatoEntrada.parse(mes);
            calendar.setTime(fecha);
            calendar.set(Calendar.YEAR, year);
            fecha = calendar.getTime();
            return fecha;
        } catch (ParseException p) {
            throw new IllegalArgumentException("Formato de mes no válido: " + mes);
        }
    }

    private List<Gasto> vaciarLista(){
        /*
        Esta funcion se encarga de vaciar la lista o retornarla si ya esta vacia.
         */
        if(this.gastos.isEmpty()){
            return this.gastos;
        }
        else{
            this.gastos.clear();
            return this.gastos;
        }
    }
}
