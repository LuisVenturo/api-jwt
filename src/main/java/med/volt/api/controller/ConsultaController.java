package med.volt.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.volt.api.domain.consulta.DatosDetalleConsulta;
import med.volt.api.domain.consulta.DatosReservaConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
public class ConsultaController {


    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){
        System.out.println(datos);
        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }
}
