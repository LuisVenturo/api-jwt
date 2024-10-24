package med.volt.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.volt.api.domain.consulta.DatosCancelamientoConsulta;
import med.volt.api.domain.consulta.DatosDetalleConsulta;
import med.volt.api.domain.consulta.DatosReservaConsulta;
import med.volt.api.domain.consulta.ReservaDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {


    @Autowired
    private ReservaDeConsultas reserva;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){
        reserva.reservar(datos);
        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos){
        reserva.c
    }

}
