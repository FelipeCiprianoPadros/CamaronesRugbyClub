package com.camarones.clubManagment.service;

import com.camarones.clubManagment.model.Cuota;
import com.camarones.clubManagment.repository.CuotaRepository;
import com.mysql.cj.x.protobuf.Mysqlx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuotaServiceTest {
    @Mock
    private CuotaRepository cuotaRepository;
    @InjectMocks
    private CuotaService cuotaService;
    private Cuota cuota;
    private Cuota cuota2;

    private Cuota cuotaActualizada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cuota = new Cuota();
        cuota.setPrecioCuota(1000);
        cuota.setMesCuota("enero");
        cuota.setPagada(false);
        cuota.setId(null);
        cuota.setFechaVencimiento(null);
        cuota.setSocio(null);
        cuota2 = new Cuota();
        cuota2.setPrecioCuota(1000);
        cuota2.setMesCuota("enero");
        cuota2.setPagada(false);
        cuota2.setFechaVencimiento(null);
        cuota2.setSocio(null);
        cuota2.setId(null);
        cuotaActualizada = new Cuota();
        cuotaActualizada.setId(1);
    }

    @Test
    void getAll() {
        // Simula el comportamiento de findAll() para devolver una lista de cuotas
        List<Cuota> cuotasMock = Arrays.asList(cuota, cuota2);
        when(cuotaRepository.findAll()).thenReturn(cuotasMock);

        // Llama al método que estás probando
        ResponseEntity<List<Cuota>> resultado = cuotaService.getAll();

        // Verifica que el método findAll() fue invocado en cuotaRepository
        verify(cuotaRepository, times(1)).findAll();

        // Añade aserciones adicionales según las expectativas de tu código
        // Por ejemplo, verifica que el resultado coincide con la lista simulada
        assertThat(resultado.getBody()).isEqualTo(cuotasMock);
    }
    @Test
    void getAllVacio() {
        when(cuotaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Cuota>> resultado = cuotaService.getAll();
        verify(cuotaRepository, times(1)).findAll();

        assertThat(resultado.getBody()).isNotNull();
    }

    @Test
    void saveCuota() {
        when(cuotaRepository.save(any())).thenReturn(cuota);

        ResponseEntity<Cuota> result = cuotaService.SaveCuota(cuota);
        verify(cuotaRepository, times(1)).save(any());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void SaveCuotaErroneo(){
        when(cuotaRepository.save(null)).thenReturn(null);

        ResponseEntity<Cuota> result = cuotaService.SaveCuota(null);
        verify(cuotaRepository, times(1)).save(any());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void updateCuota() {
        when(cuotaRepository.existsById(1)).thenReturn(true);
        when(cuotaRepository.findById(1)).thenReturn(Optional.of(cuota));
        when(cuotaRepository.save(any())).thenReturn(cuotaActualizada);


        ResponseEntity<Cuota> result = cuotaService.UpdateCuota(1, cuotaActualizada);

        verify(cuotaRepository, times(1)).existsById(1);
        verify(cuotaRepository, times(1)).findById(1);
        verify(cuotaRepository, times(1)).save(any());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void  UpdateCuotaErroneo(){

        when(cuotaRepository.existsById(1)).thenReturn(false);


        ResponseEntity<Cuota> result = cuotaService.UpdateCuota(1, cuotaActualizada);


        verify(cuotaRepository, times(1)).existsById(1);
        verify(cuotaRepository, never()).findById(any());
        verify(cuotaRepository, never()).save(any());


        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteCuota() {
        when(cuotaRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Cuota> result = cuotaService.DeleteCuota(1);

        verify(cuotaRepository, times(1)).existsById(1);
        verify(cuotaRepository, times(1)).deleteById(1);


        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void deleteCuotaErroneo(){
        when(cuotaRepository.existsById(2)).thenReturn(false);

        ResponseEntity<Cuota> result = cuotaService.DeleteCuota(2);

        verify(cuotaRepository, times(1)).existsById(2);
        verify(cuotaRepository, never()).deleteById(2); //

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void aplicarRecargoCuota() {
    }

    @Test
    void asignarCuotaAsocios() {
    }
}